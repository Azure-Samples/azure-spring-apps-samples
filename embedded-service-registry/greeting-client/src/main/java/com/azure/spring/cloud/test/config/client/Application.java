package com.azure.spring.cloud.test.config.client;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@SpringBootApplication
@RestController
@EnableFeignClients
@RequiredArgsConstructor
public class Application {

    private final HelloClient helloClient;

    @RequestMapping("/hello")
    public String hello() {
        String message = helloClient.hello();
        return String.format("client receive '%s' from server side", message);
    }

    public static void main(String [] args) {
        SpringApplication.run(Application.class, args);
    }
}
