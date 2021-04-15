package com.microsoft.sample;

import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.cloud.autoconfigure.RefreshAutoConfiguration;
import org.springframework.cloud.autoconfigure.RefreshEndpointAutoConfiguration;
import org.springframework.cloud.endpoint.RefreshEndpoint;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.stereotype.Component;

/**
 * Auto pull config changes from config server, default refresh interval is 60 seconds. This feature is disabled as
 * default, user should enable the feature by set spring.cloud.config.autoRefresh to true
 */
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
