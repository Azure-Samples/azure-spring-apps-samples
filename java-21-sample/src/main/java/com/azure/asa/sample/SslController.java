package com.azure.asa.sample;

import org.springframework.boot.ssl.SslBundle;
import org.springframework.boot.ssl.SslBundles;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.KeyStore;
import java.security.cert.X509Certificate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Enumeration;

/**
 * Exposes SSL certificate details for the configured SSL bundles.
 * Health state (UP / WARNING / DOWN) is available via the actuator:
 *   GET /actuator/health/ssl
 *
 * This endpoint provides human-readable certificate metadata:
 *   GET /ssl/info
 */
@RestController
@RequestMapping("/ssl")
public class SslController {

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss z").withZone(ZoneId.systemDefault());

    private final SslBundles sslBundles;

    public SslController(SslBundles sslBundles) {
        this.sslBundles = sslBundles;
    }

    @GetMapping("/info")
    public String sslInfo() throws Exception {
        SslBundle bundle = sslBundles.getBundle("demo");
        KeyStore keyStore = bundle.getStores().getKeyStore();

        StringBuilder sb = new StringBuilder();
        sb.append("<h3>SSL Bundle: <code>demo</code></h3>");
        sb.append("<table border='1' cellpadding='6'>");
        sb.append("<tr><th>Alias</th><th>Subject</th><th>Issuer</th><th>Valid From</th><th>Valid Until</th><th>Serial</th></tr>");

        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String alias = aliases.nextElement();
            // Java 16+ pattern matching instanceof (available in Java 21)
            if (keyStore.getCertificate(alias) instanceof X509Certificate cert) {
                sb.append("<tr>");
                sb.append("<td>").append(alias).append("</td>");
                sb.append("<td>").append(cert.getSubjectX500Principal().getName()).append("</td>");
                sb.append("<td>").append(cert.getIssuerX500Principal().getName()).append("</td>");
                sb.append("<td>").append(DATE_FMT.format(cert.getNotBefore().toInstant())).append("</td>");
                sb.append("<td>").append(DATE_FMT.format(cert.getNotAfter().toInstant())).append("</td>");
                sb.append("<td>").append(cert.getSerialNumber().toString(16)).append("</td>");
                sb.append("</tr>");
            }
        }
        sb.append("</table>");
        sb.append("<br/><p>Full health status (UP / WARNING / DOWN): ");
        sb.append("<a href='/actuator/health/ssl'>/actuator/health/ssl</a></p>");

        return sb.toString();
    }
}
