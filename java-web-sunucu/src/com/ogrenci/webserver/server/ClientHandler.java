package com.ogrenci.webserver.server;

import com.ogrenci.webserver.handler.RequestHandler;
import com.ogrenci.webserver.handler.Router;
import com.ogrenci.webserver.http.HttpRequest;
import com.ogrenci.webserver.http.HttpRequestParser;
import com.ogrenci.webserver.http.HttpResponse;
import com.ogrenci.webserver.http.HttpResponseWriter;
import com.ogrenci.webserver.http.HttpStatus;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/**
 * Tek bir istemci bağlantısını baştan sona yöneten sınıf.
 */
public class ClientHandler implements Runnable {

    private static final int READ_TIMEOUT_MS = 5000;

    private final Socket socket;
    private final Router router;
    private final HttpRequestParser parser = new HttpRequestParser();
    private final HttpResponseWriter writer = new HttpResponseWriter();

    public ClientHandler(Socket socket, Router router) {
        this.socket = socket;
        this.router = router;
    }

    @Override
    public void run() {
        // try-with-resources: blok bitince soket otomatik kapanır
        try (Socket s = socket;
             InputStream in = s.getInputStream();
             OutputStream out = s.getOutputStream()) {

            s.setSoTimeout(READ_TIMEOUT_MS);
            String client = s.getInetAddress().getHostAddress() + ":" + s.getPort();

            HttpResponse response;
            try {
                HttpRequest request = parser.parse(in);
                if (request == null) {
                    return; // Tarayıcı bağlantıyı açıp hiçbir şey göndermedi
                }
                System.out.println("[" + client + "] " + request.getMethod() + " " + request.getPath());
                RequestHandler handler = router.resolve(request.getPath());
                response = handler.handle(request);
            } catch (IllegalArgumentException e) {
                response = HttpResponse.text(HttpStatus.BAD_REQUEST, "400 - Hatalı istek");
            } catch (RuntimeException e) {
                System.err.println("İşlem hatası: " + e.getMessage());
                response = HttpResponse.text(HttpStatus.INTERNAL_SERVER_ERROR, "500 - Sunucu hatası");
            }

            writer.write(response, out);

        } catch (IOException e) {
            System.err.println("İstemci ile iletişim hatası: " + e.getMessage());
        }
    }
}
