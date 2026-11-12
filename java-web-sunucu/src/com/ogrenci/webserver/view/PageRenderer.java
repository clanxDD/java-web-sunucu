package com.ogrenci.webserver.view;

import com.ogrenci.webserver.model.StudentProfile;

/**
 * Öğrenci profilini bir sayfa metnine dönüştüren bileşenlerin ortak arayüzü.
 * Farklı bir tasarım istendiğinde bu arayüzü uygulayan yeni bir sınıf yazılabilir.
 */
public interface PageRenderer {

    String render(StudentProfile profile);
}
