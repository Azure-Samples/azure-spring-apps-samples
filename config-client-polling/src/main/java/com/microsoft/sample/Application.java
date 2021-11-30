/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
 
package com.microsoft.sample;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@EnableDiscoveryClient
@RestController
@RefreshScope
@SpringBootApplication
public class Application {

    @Value("${timeout:4000}")
    private String connectTimeout;

    @RequestMapping("/hello")
    public String hello() {
        System.out.println("hello client, connectTimeout: " + connectTimeout);
        return "hello client, connectTimeout: " + connectTimeout;
    }

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
