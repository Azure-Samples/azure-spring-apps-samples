package com.azure.spring.cloud.test.config.client;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@SpringBootApplication
@RestController
public class Application {

    @RequestMapping("/hello")
    public String hello() {
        return "server says hello";
    }

    public static void main(String [] args) {
        SpringApplication.run(Application.class, args);
    }
}
