package com.ogrenci.webserver.http;

import java.util.Collections;
import java.util.Map;

/**
 * Tarayıcıdan gelen HTTP isteğinin ayrıştırılmış (parse edilmiş) hâli.
 */
public final class HttpRequest {

    private final String method;
    private final String path;
    private final String version;
    private final Map<String, String> headers;

    public HttpRequest(String method, String path, String version, Map<String, String> headers) {
        this.method = method;
        this.path = path;
        this.version = version;
        this.headers = Collections.unmodifiableMap(headers);
    }

    public String getMethod() {
        return method;
    }

    public String getPath() {
        return path;
    }

    public String getVersion() {
        return version;
    }

    /** Başlık adları küçük harfe çevrilerek saklanır (HTTP başlıkları büyük/küçük harf duyarsızdır). */
    public String getHeader(String name) {
        return headers.get(name.toLowerCase());
    }

    public Map<String, String> getHeaders() {
        return headers;
    }
}
