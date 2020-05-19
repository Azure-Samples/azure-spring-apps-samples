/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.mynotes.spring.cloud.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class RecommendationServiceRestController {

    @RequestMapping(value = "/recommendations", method = RequestMethod.GET)
    @HystrixCommand(fallbackMethod = "recommendationFallback")
    @ResponseBody
    public List<Product> recommendations() {
        System.out.println("=======Get Called=========");
        List<Product> products = new ArrayList<Product>();
        products.add(new Product("Product1", "Description1", "link1"));
        products.add(new Product("Product2", "Description2", "link3"));
        products.add(new Product("Product3", "Description3", "link3"));
        System.out.println("=======Done processing=========");
        return products;
    }
    
    public List<Product> recommendationFallback() {
        System.out.println("=======recommendationFallback=========");
        return new ArrayList<Product>();
    }

}
