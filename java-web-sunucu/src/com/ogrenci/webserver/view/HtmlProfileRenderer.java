package com.ogrenci.webserver.view;

import com.ogrenci.webserver.model.StudentProfile;

/**
 * Öğrenci profilini renk, yazı tipi ve kutu biçimlendirmesi içeren bir HTML sayfasına çevirir.
 *
 *  - Ad Soyad  -> H1
 *  - Öğrenci No -> H2
 *  - Biçimlendirilmiş kısa biyografi (renk, font çeşidi vb.)
 */
public class HtmlProfileRenderer implements PageRenderer {

    @Override
    public String render(StudentProfile p) {
        StringBuilder html = new StringBuilder();

        html.append("<!DOCTYPE html>\n")
            .append("<html lang=\"tr\">\n")
            .append("<head>\n")
            .append("  <meta charset=\"UTF-8\">\n")
            .append("  <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n")
            .append("  <title>").append(escape(p.getFullName())).append(" | Biyografi</title>\n")
            .append("  <style>\n")
            .append(css())
            .append("  </style>\n")
            .append("</head>\n")
            .append("<body>\n")
            .append("  <main class=\"card\">\n");

        // Başlık bölümü: H1 ad soyad, H2 öğrenci numarası
        html.append("    <header>\n")
            .append("      <h1>").append(escape(p.getFullName())).append("</h1>\n")
            .append("      <h2>").append(escape(p.getStudentNumber())).append("</h2>\n")
            .append("      <p class=\"subtitle\">")
            .append(escape(p.getUniversity())).append(" &middot; ")
            .append(escape(p.getDepartment())).append(" &middot; ")
            .append(p.getGrade()).append(". Sınıf</p>\n")
            .append("    </header>\n");

        // Biyografi bölümü
        html.append("    <section class=\"bio\">\n")
            .append("      <h3>Hakkımda</h3>\n");
        String[] bio = p.getBiography();
        for (int i = 0; i < bio.length; i++) {
            String cssClass = (i == 0) ? " class=\"lead\"" : "";
            html.append("      <p").append(cssClass).append(">").append(escape(bio[i])).append("</p>\n");
        }
        html.append("    </section>\n");

        // İlgi alanları bölümü
        html.append("    <section class=\"skills\">\n")
            .append("      <h3>İlgi Alanlarım</h3>\n")
            .append("      <ul>\n");
        for (String skill : p.getSkills()) {
            html.append("        <li>").append(escape(skill)).append("</li>\n");
        }
        html.append("      </ul>\n")
            .append("    </section>\n");

        html.append("    <footer>Bu sayfa, harici kütüphane kullanılmadan Java soket programlama ile ")
            .append("yazılmış bir web sunucusu tarafından <b>1989</b> numaralı porttan sunulmaktadır.</footer>\n")
            .append("  </main>\n")
            .append("</body>\n")
            .append("</html>\n");

        return html.toString();
    }

    private String css() {
        return "    body { margin: 0; min-height: 100vh; display: flex; align-items: center; justify-content: center;\n"
             + "           background: linear-gradient(135deg, #1e3c72, #2a5298); font-family: 'Segoe UI', Tahoma, Arial, sans-serif; }\n"
             + "    .card { background: #ffffff; max-width: 720px; margin: 32px; padding: 40px 48px; border-radius: 16px;\n"
             + "            box-shadow: 0 12px 32px rgba(0, 0, 0, 0.25); }\n"
             + "    header { border-bottom: 3px solid #f39c12; padding-bottom: 16px; margin-bottom: 24px; }\n"
             + "    h1 { margin: 0; color: #1e3c72; font-family: Georgia, 'Times New Roman', serif; font-size: 2.6em; }\n"
             + "    h2 { margin: 6px 0 0; color: #f39c12; font-family: 'Courier New', monospace; font-size: 1.5em; }\n"
             + "    .subtitle { margin: 8px 0 0; color: #7f8c8d; font-style: italic; }\n"
             + "    h3 { color: #2a5298; font-variant: small-caps; letter-spacing: 1px; margin-bottom: 8px; }\n"
             + "    .bio p { color: #2c3e50; line-height: 1.7; font-size: 1.05em; text-align: justify; }\n"
             + "    .bio .lead { font-size: 1.15em; font-weight: bold; color: #16a085; }\n"
             + "    .skills ul { list-style: none; padding: 0; display: flex; flex-wrap: wrap; gap: 8px; }\n"
             + "    .skills li { background: #eaf2f8; color: #1e3c72; padding: 6px 14px; border-radius: 20px;\n"
             + "                 font-family: Verdana, sans-serif; font-size: 0.9em; border: 1px solid #aed6f1; }\n"
             + "    footer { margin-top: 28px; padding-top: 12px; border-top: 1px dashed #bdc3c7; color: #95a5a6; font-size: 0.85em; }\n";
    }

    /** HTML'de özel anlamı olan karakterleri güvenli hâle getirir (XSS'e karşı basit önlem). */
    private String escape(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
    }
}
