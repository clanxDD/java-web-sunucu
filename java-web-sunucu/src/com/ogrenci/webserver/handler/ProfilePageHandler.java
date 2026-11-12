package com.ogrenci.webserver.handler;

import com.ogrenci.webserver.http.HttpRequest;
import com.ogrenci.webserver.http.HttpResponse;
import com.ogrenci.webserver.http.HttpStatus;
import com.ogrenci.webserver.model.StudentProfile;
import com.ogrenci.webserver.view.PageRenderer;

/**
 * Ana sayfayı (ad soyad, öğrenci no, biyografi) döndüren handler.
 *
 * HTML'in nasıl üretileceğini bilmez; bu işi dışarıdan verilen {@link PageRenderer}'a bırakır.
 * Somut bir sınıfa değil arayüze bağımlıdır (Dependency Inversion).
 */
public class ProfilePageHandler implements RequestHandler {

    private final StudentProfile profile;
    private final PageRenderer renderer;

    public ProfilePageHandler(StudentProfile profile, PageRenderer renderer) {
        this.profile = profile;
        this.renderer = renderer;
    }

    @Override
    public HttpResponse handle(HttpRequest request) {
        if (!"GET".equals(request.getMethod())) {
            return HttpResponse.text(HttpStatus.METHOD_NOT_ALLOWED, "405 - Bu adres yalnızca GET isteklerini kabul eder.");
        }
        return HttpResponse.html(HttpStatus.OK, renderer.render(profile));
    }
}
