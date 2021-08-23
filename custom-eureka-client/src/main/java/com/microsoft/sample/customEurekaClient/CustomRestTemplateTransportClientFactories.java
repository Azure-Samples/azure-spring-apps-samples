/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.sample.customEurekaClient;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClientConfig;
import com.netflix.discovery.shared.transport.TransportClientFactory;
import java.util.Collection;
import java.util.Optional;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import org.springframework.cloud.netflix.eureka.http.RestTemplateTransportClientFactories;

public class CustomRestTemplateTransportClientFactories extends RestTemplateTransportClientFactories {

    @Override
    public TransportClientFactory newTransportClientFactory(final EurekaClientConfig clientConfig,
        final Collection<Void> additionalFilters, final InstanceInfo myInstanceInfo,
        final Optional<SSLContext> sslContext, final Optional<HostnameVerifier> hostnameVerifier) {
        return new CustomRestTemplateTransportClientFactory();
    }
}
