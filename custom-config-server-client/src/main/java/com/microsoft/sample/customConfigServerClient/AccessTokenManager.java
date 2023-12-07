/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.sample.customConfigServerClient;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.microsoft.azure.AzureEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import com.azure.identity.ClientSecretCredentialBuilder;

public class AccessTokenManager {
    private static String token;
    static {
        Properties prop = new Properties();
        InputStream in = AccessTokenManager.class.getResourceAsStream("/application.properties");
        try {
            prop.load(in);
            String tenantId = prop.getProperty("access.token.tenantId");
            String secret = prop.getProperty("access.token.secret");
            String clientId = prop.getProperty("access.token.clientId");
            ClientSecretCredential credential = new ClientSecretCredentialBuilder()
                    .clientId(clientId)
                    .clientSecret(secret)
                    .tenantId(tenantId)
                    .authorityHost(AzureEnvironment.AZURE.activeDirectoryEndpoint())
                    .build();
            TokenRequestContext tokenRequest = new TokenRequestContext().addScopes("https://management.core.windows.net//.default");
            token = credential.getToken(tokenRequest).block().getToken();
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static String getToken() throws IOException {
        return token;
    }
}
