/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
package com.microsoft.azure;

import com.azure.core.util.IterableStream;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.messaging.eventhubs.*;
import com.azure.messaging.eventhubs.models.EventPosition;
import com.azure.messaging.eventhubs.models.PartitionEvent;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.time.Duration;

@RestController
public class MainController {
    private EventHubProducerClient producer;
    private EventHubConsumerClient consumer;

    @Value("${spring.cloud.azure.eventhub.fully-qualifiedNamespace:local}")
    String fullyQualifiedNamespace = "xiading-asc.servicebus.windows.net";
    @Value("${spring.cloud.azure.eventhub.name:local}")
    String eventHubName = "xiading-hub";

    @PostConstruct
    private void setupSecretClient() {
        ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder()
                .maxRetry(1)
                .retryTimeout(duration -> Duration.ofMinutes(1))
                .build();

        try {
            producer = new EventHubClientBuilder()
                    .credential(fullyQualifiedNamespace, eventHubName, managedIdentityCredential)
                    .buildProducerClient();
            consumer = new EventHubClientBuilder()
                    .credential(fullyQualifiedNamespace, eventHubName, managedIdentityCredential)
                    .buildConsumerClient();
        } catch (Exception ex) {
            System.out.println("Error happens when init due to " + ex.getMessage());
        }
    }

    @PutMapping("/message")
    public String sendMessage(@RequestParam String value) {
        try {
            EventData event = new EventData(value);
            EventDataBatch eventDataBatch = producer.createBatch();
            eventDataBatch.tryAdd(event);
            producer.send(eventDataBatch);

            return String.format("Successfully send message to %s, hub %s", fullyQualifiedNamespace, eventHubName);
        } catch (Exception ex) {
            return String.format("Failed send message to %s, hub %s due to %s", fullyQualifiedNamespace, eventHubName, ex.getMessage());
        }
    }

    @GetMapping(path = "/message")
    public String receiveMessage() {
        try {
            StringBuffer sb = new StringBuffer();
            IterableStream<PartitionEvent> events = consumer.receiveFromPartition("0", 15,
                    EventPosition.earliest(), Duration.ofSeconds(40));
            for (PartitionEvent event : events) {
                sb.append("Event: " + event.getData().getBodyAsString());
            }
            return String.format("Successfully get the message from %s, hub %s: %s",
                    fullyQualifiedNamespace, eventHubName, sb.toString());
        } catch (Exception ex) {
            return String.format("Failed to get the message from %s, hub %s due to %s", fullyQualifiedNamespace, eventHubName, ex.getMessage());
        }
    }
}
