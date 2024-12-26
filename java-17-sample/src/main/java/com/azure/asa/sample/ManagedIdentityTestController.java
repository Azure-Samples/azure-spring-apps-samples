package com.azure.asa.sample;

import com.azure.core.credential.AccessToken;
import com.azure.core.credential.TokenRequestContext;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ManagedIdentityTestController {

    @GetMapping("/mi/token")
    public String getAccessToken(@RequestParam(name = "client-id", required = false) String clientId) {
        ManagedIdentityCredentialBuilder managedIdentityCredentialBuilder = new ManagedIdentityCredentialBuilder();
        if(clientId != null) {
            managedIdentityCredentialBuilder.clientId(clientId);
        }
        ManagedIdentityCredential managedIdentityCredential  = managedIdentityCredentialBuilder.build();
        AccessToken accessToken = managedIdentityCredential.getToken(new TokenRequestContext().addScopes("https://management.core.windows.net/")).block();

        return accessToken.getToken();
    }

}
