/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.sample.customConfigServerClient;

import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ClientSecretCredential;
import com.azure.identity.ClientSecretCredentialBuilder;
import com.microsoft.azure.AzureEnvironment;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

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

    public String getToken() throws IOException {
        TokenRequestContext tokenRequest = new TokenRequestContext().addScopes("https://management.core.windows.net//.default");
        ClientSecretCredential credentials = new ClientSecretCredentialBuilder()
            .clientId(clientId)
            .clientSecret(secret)
            .tenantId(tenantId)
            .authorityHost(AzureEnvironment.AZURE.activeDirectoryEndpoint())
            .build();
        return credentials.getTokenSync(tokenRequest).getToken();
    }

    public static void main(String[] args) {
        AccessTokenManager accessTokenManager = new AccessTokenManager();
        try {
            System.out.println(accessTokenManager.getToken());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
