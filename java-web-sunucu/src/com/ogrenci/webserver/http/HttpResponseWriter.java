package com.ogrenci.webserver.http;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * {@link HttpResponse} nesnesini HTTP/1.1 formatında çıkış akışına yazar.
 *
 */
public class HttpResponseWriter {

    private static final String CRLF = "\r\n";

    public void write(HttpResponse response, OutputStream out) throws IOException {
        StringBuilder head = new StringBuilder();
        head.append("HTTP/1.1 ")
            .append(response.getStatus().getCode()).append(' ')
            .append(response.getStatus().getReason()).append(CRLF);
        head.append("Server: JavaSocketWebServer/1.0").append(CRLF);
        head.append("Content-Type: ").append(response.getContentType()).append(CRLF);
        head.append("Content-Length: ").append(response.getBody().length).append(CRLF);
        head.append("Connection: close").append(CRLF);
        head.append(CRLF);

        out.write(head.toString().getBytes(StandardCharsets.US_ASCII));
        out.write(response.getBody());
        out.flush();
    }
}
