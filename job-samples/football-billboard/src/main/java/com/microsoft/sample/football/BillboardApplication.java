package com.microsoft.sample.football;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class BillboardApplication {

    public static void main(String[] args) {
        SpringApplication.run(BillboardApplication.class, args);
    }

}
