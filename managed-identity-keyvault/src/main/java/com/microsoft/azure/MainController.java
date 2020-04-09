/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.security.keyvault.secrets.SecretClient;
import com.azure.security.keyvault.secrets.SecretClientBuilder;
import com.azure.security.keyvault.secrets.models.KeyVaultSecret;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.Duration;

@RestController
public class MainController {
    private static SecretClient secretClient;

    @Value("${azure.keyvault.uri:local}")
    private String keyVaultUrl;

    @PostConstruct
    private void setupSecretClient() {
        ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
                .maxRetry(1)
                .retryTimeout(duration -> Duration.ofMinutes(1))
                .build();

        secretClient = new SecretClientBuilder()
                .vaultUrl(keyVaultUrl)
                .credential(managedIdentityCredential)
                .buildClient();
    }

    @PutMapping("/secret/{name}")
    public String setSecret(@PathVariable String name, @RequestParam String value) {
        try {
            KeyVaultSecret secret = secretClient.setSecret(name, value);
            return String.format("Successfully set secret %s in Key Vault %s", name, keyVaultUrl);
        } catch (Exception ex) {
            return String.format("Failed to set secret %s in Key Vault %s due to %s", name,
                    keyVaultUrl, ex.getMessage());
        }
    }

    @GetMapping(path="/secret/{name}")
    public String getSecret(@PathVariable String name) {
        try {
            KeyVaultSecret secret = secretClient.getSecret(name);
            return String.format("Successfully got the value of secret %s from Key Vault %s: %s",
                    name, keyVaultUrl, secret.getValue());
        } catch (Exception ex) {
            return String.format("Failed to get secret %s from Key Vault %s due to %s", name,
                    keyVaultUrl, ex.getMessage());
        }
    }
}
