package com.ogrenci.webserver.http;

import java.nio.charset.StandardCharsets;

/**
 * Sunucunun tarayıcıya göndereceği yanıtı temsil eder: durum kodu, içerik türü ve gövde.
 * Yanıtın sokete nasıl yazılacağı bu sınıfın değil {@link HttpResponseWriter}'ın işidir.
 */
public final class HttpResponse {

    private final HttpStatus status;
    private final String contentType;
    private final byte[] body;

    public HttpResponse(HttpStatus status, String contentType, byte[] body) {
        this.status = status;
        this.contentType = contentType;
        this.body = body;
    }

    /** HTML yanıtı oluşturan yardımcı metot. */
    public static HttpResponse html(HttpStatus status, String html) {
        return new HttpResponse(status, "text/html; charset=UTF-8", html.getBytes(StandardCharsets.UTF_8));
    }

    /** Düz metin yanıtı oluşturan yardımcı metot. */
    public static HttpResponse text(HttpStatus status, String text) {
        return new HttpResponse(status, "text/plain; charset=UTF-8", text.getBytes(StandardCharsets.UTF_8));
    }

    public HttpStatus getStatus() {
        return status;
    }

    public String getContentType() {
        return contentType;
    }

    public byte[] getBody() {
        return body;
    }
}
