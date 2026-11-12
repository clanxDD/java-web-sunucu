package com.ogrenci.webserver.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * Soketten gelen ham metni okuyup {@link HttpRequest} nesnesine dönüştürür.
 */
public class HttpRequestParser {

    private static final int MAX_HEADERS = 100;

    /**
     * @return Ayrıştırılmış istek; bağlantı boş kapanırsa null
     * @throws IllegalArgumentException istek satırı HTTP formatına uymazsa
     */
    public HttpRequest parse(InputStream input) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(input, StandardCharsets.UTF_8));

        String requestLine = reader.readLine();
        if (requestLine == null || requestLine.isEmpty()) {
            return null;
        }

        String[] parts = requestLine.split(" ");
        if (parts.length != 3) {
            throw new IllegalArgumentException("Geçersiz istek satırı: " + requestLine);
        }

        String method = parts[0];
        String path = stripQuery(parts[1]);
        String version = parts[2];

        Map<String, String> headers = new HashMap<>();
        String line;
        int count = 0;
        while ((line = reader.readLine()) != null && !line.isEmpty() && count < MAX_HEADERS) {
            int colon = line.indexOf(':');
            if (colon > 0) {
                String name = line.substring(0, colon).trim().toLowerCase();
                String value = line.substring(colon + 1).trim();
                headers.put(name, value);
            }
            count++;
        }

        return new HttpRequest(method, path, version, headers);
    }

    /** "/index.html?ad=ali" gibi bir adresten sorgu kısmını atar. */
    private String stripQuery(String target) {
        int q = target.indexOf('?');
        return q >= 0 ? target.substring(0, q) : target;
    }
}
