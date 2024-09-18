package com.azure.asa.sample;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        StringBuilder sb = new StringBuilder();
        sb.append("<h1>Greetings from Spring Boot!</h1>");

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