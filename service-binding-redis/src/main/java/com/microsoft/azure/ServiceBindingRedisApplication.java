/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.stream.Stream;

@EnableDiscoveryClient
@SpringBootApplication
@Service
public class ServiceBindingRedisApplication implements CommandLineRunner {

	@Autowired
	StringRedisTemplate redis;

	@Autowired
	RedisConnectionFactory factory;

	public static void main(String[] args) {
		SpringApplication.run(ServiceBindingRedisApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		factory.getConnection().flushAll();
		Stream.of("Alice", "Bob", "Smith")
				.forEach(name -> redis.opsForValue().set(UUID.randomUUID().toString(), name));
	}
}
