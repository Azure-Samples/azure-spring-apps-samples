package com.example.springboot;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.config.client.ConfigClientProperties;
import org.springframework.cloud.config.client.ConfigServicePropertySourceLocator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.client.RestTemplate;

@Configuration
public class CustomConfigServiceBootstrapConfiguration {
    private static String configServerEndpoint;

    static {
        Properties prop = new Properties();
        InputStream in = CustomConfigServiceBootstrapConfiguration.class.getResourceAsStream("/application.properties");
        try {
            prop.load(in);
            configServerEndpoint = prop.getProperty("spring.cloud.config.uri");
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    @Autowired
    private Environment environment;

    @Bean
    public ConfigServicePropertySourceLocator configServicePropertySourceLocator() {
        ConfigClientProperties clientProperties = new ConfigClientProperties(this.environment);
        clientProperties.setUri(new String[]{configServerEndpoint});
        ConfigServicePropertySourceLocator configServicePropertySourceLocator =  new ConfigServicePropertySourceLocator(clientProperties);
        configServicePropertySourceLocator.setRestTemplate(customRestTemplate());
        return configServicePropertySourceLocator;
    }

    public class RequestResponseHandlerInterceptor implements ClientHttpRequestInterceptor {

        private static final String AUTHORIZATION = "Authorization";

        /**
         * This method will intercept every request and response and based on response status code if its 401 then will retry
         * once
         */

        @Override
        public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
            String accessToken = AccessTokenManager.getToken();
            request.getHeaders().remove(AUTHORIZATION);
            request.getHeaders().add(AUTHORIZATION, "Bearer " + accessToken);

            ClientHttpResponse response = execution.execute(request, body);
            return response;
        }

    }

    private RestTemplate customRestTemplate() {
        /*
         * Inject your custom rest template
         */
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.getInterceptors()
            .add(new RequestResponseHandlerInterceptor());

        return restTemplate;
    }
}
