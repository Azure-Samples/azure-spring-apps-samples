package com.azure.asa.sample;

import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class EurekaController {

    @Autowired(required = false)
    private EurekaClient discoveryClient;

    @GetMapping("/eureka")
    public String getEurekaState() {
        StringBuilder sb = new StringBuilder();

        sb.append("<p>Known Regions</p>");
        sb.append("<ul>");
        Set<String> regions = discoveryClient.getAllKnownRegions();
        for(String region : regions) {
            sb.append("<li>").append(region).append("</li>");
        }
        sb.append("</ul>");

        List<Application> apps = discoveryClient.getApplications().getRegisteredApplications();
        sb.append("<p>Registered Applications</p>");
        sb.append("<ul>");
        for(Application app : apps) {
            sb.append("<p>Application=").append(app.getName()).append("</p>");
            sb.append("<ul>");
            List<InstanceInfo> instances = app.getInstances();
            for(InstanceInfo instance : instances) {
                sb.append("<ul>");
                sb.append("<li>InstanceId=").append(instance.getInstanceId()).append("</li>");
                sb.append("<li>Status=").append(instance.getStatus().toString()).append("</li>");
                sb.append("<li>IPAddr=").append(instance.getIPAddr()).append("</li>");
                sb.append("</ul>");
            }
            sb.append("</ul>");
        }
        sb.append("</ul>");

        sb.append("<p>Client Configuration</p>");
        sb.append("<ul>").append(discoveryClient.getEurekaClientConfig().toString()).append("</ul>");

        return sb.toString();
    }
}
