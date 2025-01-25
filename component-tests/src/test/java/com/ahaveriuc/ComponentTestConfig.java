package com.ahaveriuc;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.util.DefaultUriBuilderFactory;

@Configuration
@ConditionalOnProperty(
        name = "embedded.service.enabled",
        havingValue = "true",
        matchIfMissing = true
)
@DependsOn("service")
public class ComponentTestConfig {

    @Value("${embedded.service.host}")
    String host;

    @Value("${embedded.service.port}")
    String port;

    @Bean
    TestRestTemplate testRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplateBuilder);

        DefaultUriBuilderFactory defaultUriBuilderFactory = new DefaultUriBuilderFactory(String.format("http://%s:%s", host, port));
        testRestTemplate.setUriTemplateHandler(defaultUriBuilderFactory);

        return testRestTemplate;
    }

    @Bean
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }
}
