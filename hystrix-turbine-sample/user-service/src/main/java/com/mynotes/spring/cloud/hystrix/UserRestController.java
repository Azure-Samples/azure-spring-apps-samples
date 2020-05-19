/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.mynotes.spring.cloud.hystrix;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class UserRestController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping(value = "/personalized/{id}")
    @HystrixCommand(fallbackMethod = "recommendationFallback")
    public Product[] personalized(@PathVariable int id) {

        Product[] result = new Product[0];

        System.out.println("=======Calling=========" + id);
        try{
        result = restTemplate.getForObject("http://recommendation-service/recommendations", Product[].class);
        }catch (Exception e) {
            System.out.println(e);
        }
        System.out.println("=======Done Calling=========" + result);

        return result;
    }

    public Product[] recommendationFallback(int id) {
        System.out.println("=======recommendationFallback=========" + id);
        return new Product[0];
    }

}
