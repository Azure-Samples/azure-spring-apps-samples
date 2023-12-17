/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.sample.customConfigServerClient;

import java.io.IOException;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;

public class AuthorizationHeaderRequestInterceptor implements ClientHttpRequestInterceptor {

    public AuthorizationHeaderRequestInterceptor() {
        accessTokenManager = new AccessTokenManager();
    }

    private AccessTokenManager accessTokenManager;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution)
            throws IOException {
        if (!request.getHeaders().containsKey("Authorization")) {
            request.getHeaders().add("Authorization", "Bearer " + accessTokenManager.getToken());
        }
        return execution.execute(request, body);
    }

}
