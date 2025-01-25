package com.ahaveriuc.componenttests.tests;


import com.ahaveriuc.componenttests.ComponentTest;
import org.assertj.core.api.SoftAssertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@ComponentTest
class HealthTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Value("${embedded.service.host}")
    String host;
    @Value("${embedded.service.management.port}")
    int port;

    @Test
    void health() {

        ResponseEntity<String> response = restTemplate.getForEntity("http://%s:%s/health".formatted(host, port), String.class);

        assertExpectedResponse(response, HttpStatus.OK, "{\"status\":\"UP\"}");
    }

    private void assertExpectedResponse(ResponseEntity<String> response, HttpStatus expectedStatus, String expectedBody) {
        SoftAssertions.assertSoftly(softAssertions -> {
            softAssertions.assertThat(response.getStatusCode())
                    .as("check status")
                    .isEqualTo(expectedStatus);

            softAssertions.assertThat(response.getBody())
                    .as("check body")
                    .isEqualTo(expectedBody);
        });
    }
}
