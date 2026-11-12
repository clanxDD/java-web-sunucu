package com.ogrenci.webserver;

import com.ogrenci.webserver.config.ServerConfig;
import com.ogrenci.webserver.handler.NotFoundHandler;
import com.ogrenci.webserver.handler.ProfilePageHandler;
import com.ogrenci.webserver.handler.Router;
import com.ogrenci.webserver.model.StudentProfile;
import com.ogrenci.webserver.server.WebServer;
import com.ogrenci.webserver.view.HtmlProfileRenderer;
import com.ogrenci.webserver.view.PageRenderer;

import java.io.IOException;

/**
 * Bu sınıf sadece nesneleri oluşturur ve birbirine bağlar (Composition Root).
 * İş mantığı içermez; böylece her bileşen kendi işine odaklanır (SOLID - S ve D).
 */
public class Main {

    public static void main(String[] args) {
        // 1) Sunucu ayarları: port 1989
        ServerConfig config = new ServerConfig(1989, 10);

        // 2) Sayfada gösterilecek öğrenci bilgileri
        StudentProfile profile = new StudentProfile(
                "Mehmet Akif Gök",
                "1250505047",
                "Kırklareli Üniversitesi",
                "Yazılım Mühendisliği",
                2,
                new String[]{
                        "Kırklareli Üniversitesi Yazılım Mühendisliği bölümünde 2. sınıf öğrencisiyim.",
                        "Nesneye yönelik programlama, veritabanı sistemleri ve web programlama derslerinde "
                                + "öğrendiklerimi küçük projelerle pekiştirmeye çalışıyorum.",
                        "Boş zamanlarımda ESP32 gibi mikrodenetleyicilerle basit IoT projeleri geliştiriyorum."
                },
                new String[]{"Java", "SQL", "HTML / CSS", "JavaScript", "Gömülü Sistemler (ESP32)"}
        );

        // 3) HTML üretici (arayüz tipinde tutulur, istenirse başka bir tasarımla değiştirilebilir)
        PageRenderer renderer = new HtmlProfileRenderer();

        // 4) Yönlendirici: hangi adres hangi handler'a gidecek?
        Router router = new Router(new NotFoundHandler());
        ProfilePageHandler profileHandler = new ProfilePageHandler(profile, renderer);
        router.register("/", profileHandler);
        router.register("/index.html", profileHandler);

        // 5) Sunucuyu başlat
        WebServer server = new WebServer(config, router);
        Runtime.getRuntime().addShutdownHook(new Thread(server::stop));

        try {
            server.start();
        } catch (IOException e) {
            System.err.println("Sunucu başlatılamadı: " + e.getMessage());
            System.err.println("Port " + config.getPort() + " başka bir program tarafından kullanılıyor olabilir.");
        }
    }
}
