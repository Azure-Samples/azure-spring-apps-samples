/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class MainController {
    private final StringRedisTemplate redis;

    public MainController(StringRedisTemplate redis) {
        this.redis = redis;
    }

    @GetMapping("/all")
    public List<String> all() {
        return redis.keys("*").stream().sorted().collect(Collectors.toList());
    }

    @GetMapping(path = "/hello")
    public String hello() {
        return "hello!";
    }
}
