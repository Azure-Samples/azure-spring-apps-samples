/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.stereotype.Service;

@EnableDiscoveryClient
@SpringBootApplication
@Service
public class ServiceBindingRedisApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServiceBindingRedisApplication.class, args);
    }
}
