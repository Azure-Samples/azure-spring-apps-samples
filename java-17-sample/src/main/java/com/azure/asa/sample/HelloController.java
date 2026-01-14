package com.azure.asa.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

@RestController
public class HelloController {

    @Value("#{environment['ASCSVCRT_SPRING__APPLICATION__NAME']}")
    private String appName;

    @GetMapping("/")
    public String index() {
        StringBuilder sb = new StringBuilder();
        sb.append("<p>Greetings from Azure Spring Apps instance ");
        sb.append(appName);
        sb.append(" at ");
        sb.append(Instant.now().toString());
        sb.append("</p>");

        return sb.toString();
    }

    private List<byte[]> memory = new ArrayList<>();

    @PostMapping("/memory/add")
    public String increaseMemory() {
        byte[] bytes = new byte[32 * 1024 * 1024];
        memory.add(bytes);
        return "Current length is " + memory.size();
    }
}