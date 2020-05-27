/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.BlobServiceClientBuilder;
import com.azure.storage.blob.specialized.BlockBlobClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Locale;

@RestController
public class MainController {
    private BlobContainerClient blobContainerClient;

    @Value("${azure.storage.account:local}")
    private String accountName;

    @Value("${azure.storage.container:local}")
    private String containerName;

    @PostConstruct
    private void setupBlobClient() {
        ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
                .maxRetry(1)
                .retryTimeout(duration -> Duration.ofMinutes(1))
                .build();

        String endpoint = String.format(Locale.ROOT, "https://%s.blob.core.windows.net", accountName);
        BlobServiceClient storageClient = new BlobServiceClientBuilder().endpoint(endpoint).credential(managedIdentityCredential).buildClient();
        blobContainerClient = storageClient.getBlobContainerClient(containerName);
        if (!blobContainerClient.exists()) {
            blobContainerClient.create();
        }
    }

    @PutMapping("/blob/{name}")
    public String uploadBlob(@PathVariable String name, @RequestParam String content) {
        try {
            BlockBlobClient blobClient = blobContainerClient.getBlobClient(name).getBlockBlobClient();
            InputStream dataStream = new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8));
            blobClient.upload(dataStream, content.length());

            dataStream.close();

            return String.format("Successfully upload blob %s in storage account %s container %s", name, accountName, containerName);
        } catch (Exception ex) {
            return String.format("Failed to upload blob %s in storage account %s container %s due to %s", name,
                    accountName, containerName, ex.getMessage());
        }
    }

    @GetMapping(path="/blob/{name}")
    public String downloadBlob(@PathVariable String name) {
        try {
            BlockBlobClient blobClient = blobContainerClient.getBlobClient(name).getBlockBlobClient();
            int dataSize = (int) blobClient.getProperties().getBlobSize();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream(dataSize);
            blobClient.download(outputStream);
            outputStream.close();

            return String.format("Successfully got the content of blob %s from storage account %s container %s: %s",
                    name, accountName, containerName, new String(outputStream.toByteArray()));
        } catch (Exception ex) {
            return String.format("Failed to download blob %s from storage account %s container %s due to %s",
                    name, accountName, containerName, ex.getMessage());
        }
    }
}
