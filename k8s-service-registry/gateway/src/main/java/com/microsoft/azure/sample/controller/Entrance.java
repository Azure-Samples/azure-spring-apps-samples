package com.microsoft.azure.sample.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.HandlerMapping;

@RestController
public class Entrance {

    @Value("${spring.application.name}")
    private String appName;

    @Bean
    RestTemplate getRestTemplate() {
        return new RestTemplate();
    }

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String index() {
        return appName;
    }

    @RequestMapping(path = "/{appName}/**", method = RequestMethod.GET)
    public String redirect(@PathVariable String appName, HttpServletRequest request) {

        String requestPath = (String) request.getAttribute(HandlerMapping.PATH_WITHIN_HANDLER_MAPPING_ATTRIBUTE);
        String appPath = "/";
        int start = requestPath.indexOf("/", requestPath.indexOf("/") + 1);
        if (start > 0) {
            appPath = requestPath.substring(start);
        }

        RestTemplate restTemplate = new RestTemplate();
        return restTemplate.getForObject(String.format("http://%s%s", appName, appPath), String.class);
    }
}
