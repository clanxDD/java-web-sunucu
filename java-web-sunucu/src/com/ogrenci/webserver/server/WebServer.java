package com.ogrenci.webserver.server;

import com.ogrenci.webserver.config.ServerConfig;
import com.ogrenci.webserver.handler.Router;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Belirtilen portu dinleyen TCP sunucusu.
 *
 * Görevi yalnızca bağlantı kabul etmektir. Gelen her bağlantı bir {@link ClientHandler}
 * nesnesine sarılır ve iş parçacığı havuzunda (thread pool) çalıştırılır.
 * Böylece bir tarayıcı beklerken diğerleri de hizmet alabilir.
 */
public class WebServer {

    private final ServerConfig config;
    private final Router router;
    private final ExecutorService threadPool;

    private volatile boolean running;
    private ServerSocket serverSocket;

    public WebServer(ServerConfig config, Router router) {
        this.config = config;
        this.router = router;
        this.threadPool = Executors.newFixedThreadPool(config.getThreadPoolSize());
    }

    /** Sunucuyu başlatır; stop() çağrılana kadar bağlantı kabul eder. */
    public void start() throws IOException {
        serverSocket = new ServerSocket(config.getPort());
        running = true;

        System.out.println("Sunucu çalışıyor  -> http://localhost:" + config.getPort());
        System.out.println("Durdurmak için Ctrl + C");

        while (running) {
            try {
                Socket clientSocket = serverSocket.accept();   // Bir istemci gelene kadar burada bekler
                threadPool.execute(new ClientHandler(clientSocket, router));
            } catch (SocketException e) {
                if (running) {
                    System.err.println("Bağlantı hatası: " + e.getMessage());
                }
            }
        }
    }

    /** Sunucuyu ve iş parçacığı havuzunu düzgün şekilde kapatır. */
    public void stop() {
        running = false;
        try {
            if (serverSocket != null && !serverSocket.isClosed()) {
                serverSocket.close();
            }
        } catch (IOException ignored) {
            // Kapanış sırasında oluşan hata önemsizdir
        }
        threadPool.shutdown();
        try {
            threadPool.awaitTermination(3, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        System.out.println("Sunucu durduruldu.");
    }
}
