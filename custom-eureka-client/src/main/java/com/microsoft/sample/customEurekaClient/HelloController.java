/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License. See LICENSE in the project root for
 * license information.
 */

package com.microsoft.sample.customEurekaClient;

import java.util.List;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.netflix.eureka.http.RestTemplateDiscoveryClientOptionalArgs;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
public class HelloController {

	@Autowired
	private RestTemplate restTemplate;

	@Bean
	public RestTemplateDiscoveryClientOptionalArgs discoveryClientOptionalArgs() throws Exception {
		RestTemplateDiscoveryClientOptionalArgs args = new RestTemplateDiscoveryClientOptionalArgs();
		args.setHostnameVerifier(NoopHostnameVerifier.INSTANCE);
		args.setTransportClientFactories(new CustomRestTemplateTransportClientFactories());
		return args;
	}

	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	@Autowired
	private DiscoveryClient discoveryClient;

	@RequestMapping("/{applicationName}")
	public List<ServiceInstance> serviceInstancesByApplicationName(
		@PathVariable String applicationName) {
		return this.discoveryClient.getInstances(applicationName);
	}
}
