/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.sample.customConfigServerClient;

import java.io.IOException;
import java.io.InputStream;
import java.time.OffsetDateTime;
import java.util.Properties;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenCredential;
import com.azure.core.credential.TokenRequestContext;
import com.azure.core.management.AzureEnvironment;
import com.azure.identity.ClientSecretCredentialBuilder;

public class AccessTokenManager {

    static {
        Properties prop = new Properties();
        InputStream in = AccessTokenManager.class.getResourceAsStream("/application.properties");
        try {
            prop.load(in);

            clientId = prop.getProperty("access.token.clientId");
            tenantId = prop.getProperty("access.token.tenantId");
            secret = prop.getProperty("access.token.secret");

        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String clientId;

    private static String secret;

    private static String tenantId;

    private AccessToken token;

    public String getToken() throws IOException {
        if (token == null || token.getExpiresAt().isBefore(OffsetDateTime.now().plusMinutes(5))) {
            TokenCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(clientId)
                    .clientSecret(secret)
                    .tenantId(tenantId)
                    .build();
            token = credential.getTokenSync(new TokenRequestContext()
                    .setTenantId(tenantId)
                    .addScopes(String.format("%s/.default", AzureEnvironment.AZURE.getManagementEndpoint())));
        }
        return token.getToken();
    }
}
