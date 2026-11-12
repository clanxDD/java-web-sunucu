package com.ogrenci.webserver.handler;

import java.util.HashMap;
import java.util.Map;

/**
 * İstenen adrese (path) göre hangi {@link RequestHandler}'ın çalışacağına karar verir.
 * Kayıtlı olmayan adresler için varsayılan handler (404 sayfası) döner.
 */
public class Router {

    private final Map<String, RequestHandler> routes = new HashMap<>();
    private final RequestHandler fallback;

    public Router(RequestHandler fallback) {
        this.fallback = fallback;
    }

    public void register(String path, RequestHandler handler) {
        routes.put(path, handler);
    }

    public RequestHandler resolve(String path) {
        RequestHandler handler = routes.get(path);
        return handler != null ? handler : fallback;
    }
}
