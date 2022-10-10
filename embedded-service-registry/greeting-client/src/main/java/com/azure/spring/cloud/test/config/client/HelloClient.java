package com.azure.spring.cloud.test.config.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "${greeting.server.name}", url = "http://${greeting.server.name}/")
public interface HelloClient {

    @RequestMapping(method = RequestMethod.GET, value = "/hello")
    String hello();
}
