package com.azure.asa.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @Value("#{environment['ASCSVCRT_SPRING__APPLICATION__NAME']}")
    private String appName;

    // Java 21 feature: record as a local data carrier
    record Greeting(String instance, String timestamp, String javaVersion) {}

    @GetMapping("/")
    public String index() {
        Greeting greeting = new Greeting(appName, Instant.now().toString(), Runtime.version().toString());

        StringBuilder sb = new StringBuilder();
        sb.append("<p>Greetings from Azure Spring Apps instance ");
        sb.append(greeting.instance());
        sb.append(" at ");
        sb.append(greeting.timestamp());
        sb.append("</p>");
        sb.append("<p>Running on Java ");
        sb.append(greeting.javaVersion());
        sb.append("</p>");

        return sb.toString();
    }

    @GetMapping("/java-version")
    public String javaVersion() {
        // Java 21 feature: text blocks and switch expression
        Runtime.Version version = Runtime.version();
        String tier = switch (version.feature()) {
            case 21 -> "LTS (Java 21)";
            case 17 -> "LTS (Java 17)";
            case 11 -> "LTS (Java 11)";
            default -> "Non-LTS (Java " + version.feature() + ")";
        };

        return """
                <p>Java Version: %s</p>
                <p>Release Tier: %s</p>
                <p>Virtual Threads: enabled</p>
                """.formatted(version, tier);
    }

    private List<byte[]> memory = new ArrayList<>();

    @PostMapping("/memory/add")
    public String increaseMemory() {
        byte[] bytes = new byte[32 * 1024 * 1024];
        memory.add(bytes);
        return "Current length is " + memory.size();
    }
}
