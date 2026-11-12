package com.ogrenci.webserver.config;

/**
 * Sunucunun ayarlarını tutan değişmez (immutable) sınıf.
 * Port numarası ve aynı anda kaç istemciye hizmet verileceği burada tanımlanır.
 */
public final class ServerConfig {

    private final int port;
    private final int threadPoolSize;

    public ServerConfig(int port, int threadPoolSize) {
        if (port < 1 || port > 65535) {
            throw new IllegalArgumentException("Port 1 ile 65535 arasında olmalıdır: " + port);
        }
        if (threadPoolSize < 1) {
            throw new IllegalArgumentException("İş parçacığı sayısı en az 1 olmalıdır.");
        }
        this.port = port;
        this.threadPoolSize = threadPoolSize;
    }

    public int getPort() {
        return port;
    }

    public int getThreadPoolSize() {
        return threadPoolSize;
    }
}
