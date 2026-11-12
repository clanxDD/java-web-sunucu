package com.ogrenci.webserver.handler;

import com.ogrenci.webserver.http.HttpRequest;
import com.ogrenci.webserver.http.HttpResponse;

/**
 * Bir HTTP isteğine yanıt üreten her sınıfın uyması gereken sözleşme.
 *
 * Yeni bir sayfa eklemek için mevcut kodu değiştirmeye gerek yoktur;
 * bu arayüzü uygulayan yeni bir sınıf yazıp Router'a kaydetmek yeterlidir (Open/Closed).
 */
public interface RequestHandler {

    HttpResponse handle(HttpRequest request);
}
