/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.sample.customConfigServerClient;

import com.microsoft.azure.AzureEnvironment;
import com.microsoft.azure.credentials.ApplicationTokenCredentials;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AccessTokenManager {

    static {
        Properties prop = new Properties();
        InputStream in = AccessTokenManager.class.getResourceAsStream("/application.properties");
        try {
            prop.load(in);
            tokenClientId = prop.getProperty("access.token.clientId");
            String tenantId = prop.getProperty("access.token.tenantId");
            String secret = prop.getProperty("access.token.secret");
            String clientId = prop.getProperty("access.token.clientId");
            credentials = new ApplicationTokenCredentials(
                clientId, tenantId, secret, AzureEnvironment.AZURE);
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    private static String tokenClientId;

    private static ApplicationTokenCredentials credentials;

    public static String getToken() throws IOException {
        return credentials.getToken(tokenClientId);
    }
}
