/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */
 
package com.microsoft.sample;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshEndpointAutoConfiguration;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

@Component
@AutoConfigureAfter({RefreshAutoConfiguration.class, RefreshEndpointAutoConfiguration.class})
@EnableScheduling
public class ConfigClientAutoRefreshConfiguration implements SchedulingConfigurer {

    private RefreshEndpoint refreshEndpoint;

    public ConfigClientAutoRefreshConfiguration(RefreshEndpoint refreshEndpoint) {
        this.refreshEndpoint = refreshEndpoint;
    }

    @Override
    public void configureTasks(ScheduledTaskRegistrar scheduledTaskRegistrar) {
        // set refresh interval to 10 seconds
        int refreshInterval = 10;
        scheduledTaskRegistrar.addFixedRateTask(() -> refreshEndpoint.refresh(), refreshInterval * 1000);
    }

}
