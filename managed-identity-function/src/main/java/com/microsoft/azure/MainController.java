/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ChainedTokenCredentialBuilder;
import com.azure.identity.EnvironmentCredential;
import com.azure.identity.EnvironmentCredentialBuilder;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class MainController {

    private static final Logger LOGGER = LoggerFactory.getLogger(MainController.class);

    @Value("${azure.function.uri:local}")
    private String functionUri;
    @Value("${azure.function.application-id.uri}")
    private String applicationIdUri;
    @Value("${azure.function.triggerPath}")
    private String triggerPath;


    @GetMapping(path="/func/{name}")
    public String invokeFunction(@PathVariable String name) {
        try {
            final EnvironmentCredential environmentCredential = new EnvironmentCredentialBuilder().build();
            final ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().build();
            TokenCredential tokenCredential = new ChainedTokenCredentialBuilder()
                    .addLast(environmentCredential)
                    .addLast(managedIdentityCredential)
                    .build();

            TokenRequestContext tokenRequestContext = new TokenRequestContext();
            tokenRequestContext.addScopes(this.applicationIdUri + "/.default");
            final AccessToken accessToken = tokenCredential.getTokenSync(tokenRequestContext);

            RestTemplate restTemplate = new RestTemplate();
            HttpHeaders headers = new HttpHeaders();
            headers.setBearerAuth(accessToken.getToken());
            HttpEntity<String> entity = new HttpEntity<>(null, headers);

            String requestUri = String.format("%s/api/%s?name=%s", this.functionUri, this.triggerPath, name);
            String result = restTemplate.exchange(requestUri, HttpMethod.GET, entity, String.class).getBody();
            return result;
        } catch (Exception ex) {
            LOGGER.error("Fail to call the function", ex);
            return String.format("Failed to invoke function %s", this.functionUri);
        }
    }


}
