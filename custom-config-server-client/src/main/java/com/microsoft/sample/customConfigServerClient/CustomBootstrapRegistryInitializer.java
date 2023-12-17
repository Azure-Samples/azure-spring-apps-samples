/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.sample.customConfigServerClient;

import java.util.ArrayList;
import java.util.List;

import org.springframework.boot.BootstrapRegistry;
import org.springframework.boot.BootstrapRegistryInitializer;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.web.client.RestTemplate;

public class CustomBootstrapRegistryInitializer implements BootstrapRegistryInitializer {

    @Override
    public void initialize(BootstrapRegistry registry) {
        registry.register(RestTemplate.class, context -> {
            RestTemplate template = new RestTemplate();
            List<ClientHttpRequestInterceptor> interceptors = template.getInterceptors();
            if (interceptors == null) {
                interceptors = new ArrayList<>();
            }
            interceptors.add(new AuthorizationHeaderRequestInterceptor());
            template.setInterceptors(interceptors);
            return template;
        });
    }

}