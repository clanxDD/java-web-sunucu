package com.ogrenci.webserver.handler;

import com.ogrenci.webserver.http.HttpRequest;
import com.ogrenci.webserver.http.HttpResponse;
import com.ogrenci.webserver.http.HttpStatus;

/** Tanımlı olmayan bir adres istendiğinde 404 sayfası döndürür. */
public class NotFoundHandler implements RequestHandler {

    @Override
    public HttpResponse handle(HttpRequest request) {
        String html = "<!DOCTYPE html><html lang=\"tr\"><head><meta charset=\"UTF-8\">"
                + "<title>404 - Sayfa Bulunamadı</title></head>"
                + "<body style=\"font-family: Arial, sans-serif; text-align: center; padding-top: 60px;\">"
                + "<h1 style=\"color: #c0392b;\">404</h1>"
                + "<p>Aradığınız sayfa bulunamadı.</p>"
                + "<a href=\"/\">Ana sayfaya dön</a>"
                + "</body></html>";
        return HttpResponse.html(HttpStatus.NOT_FOUND, html);
    }
}
