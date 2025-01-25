package com.ahaveriuc.componenttests;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.client.RootUriTemplateHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.http.HttpHeaders;
import org.springframework.security.oauth2.client.AuthorizedClientServiceOAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizeRequest;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientManager;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProvider;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientProviderBuilder;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.OAuth2AccessToken;

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

    @Value("${AUTH_TOKEN_URL}")
    String tokenUrl;

    @Bean
    TestRestTemplate testRestTemplate(RestTemplateBuilder restTemplateBuilder, OAuth2AuthorizedClientManager authorizedClientManager, ClientRegistrationRepository clientRegistrationRepository) {

        TestRestTemplate testRestTemplate = new TestRestTemplate(restTemplateBuilder.interceptors((request, body, execution) -> {

            if (!request.getHeaders().containsKey(HttpHeaders.AUTHORIZATION)) {

                ClientRegistration clientRegistration = clientRegistrationRepository.findByRegistrationId("service");

                OAuth2AuthorizeRequest authorizeRequest = OAuth2AuthorizeRequest
                        .withClientRegistrationId(clientRegistration.getRegistrationId())
                        .principal("client")
                        .build();

                OAuth2AuthorizedClient authorizedClient = authorizedClientManager.authorize(authorizeRequest);

                OAuth2AccessToken accessToken = authorizedClient.getAccessToken();
                String token = accessToken.getTokenValue();

                request.getHeaders().setBearerAuth(token);

            }
            return execution.execute(request, body);
        }));


        RootUriTemplateHandler rootUriTemplateHandler = new RootUriTemplateHandler(String.format("http://%s:%s", host, port));
        testRestTemplate.setUriTemplateHandler(rootUriTemplateHandler);

        return testRestTemplate;
    }

    @Bean
    RestTemplateBuilder restTemplateBuilder() {
        return new RestTemplateBuilder();
    }

    @Bean
    public ClientRegistration clientRegistration() {
        return ClientRegistration.withRegistrationId("service")
                .clientId("service")
                .clientSecret("secret")
                .tokenUri(tokenUrl)
                .authorizationGrantType(AuthorizationGrantType.CLIENT_CREDENTIALS)
                .scope("service")
                .build();
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        return new InMemoryClientRegistrationRepository(clientRegistration());
    }

    @Bean
    public OAuth2AuthorizedClientManager authorizedClientManager(
            ClientRegistrationRepository clientRegistrationRepository,
            OAuth2AuthorizedClientService authorizedClientService) {

        OAuth2AuthorizedClientProvider authorizedClientProvider =
                OAuth2AuthorizedClientProviderBuilder.builder()
                        .clientCredentials()
                        .build();

        AuthorizedClientServiceOAuth2AuthorizedClientManager authorizedClientManager =
                new AuthorizedClientServiceOAuth2AuthorizedClientManager(
                        clientRegistrationRepository, authorizedClientService);

        authorizedClientManager.setAuthorizedClientProvider(authorizedClientProvider);

        return authorizedClientManager;
    }

    @Bean
    InMemoryOAuth2AuthorizedClientService inMemoryOAuth2AuthorizedClientService(ClientRegistrationRepository clientRegistrationRepository) {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository);
    }
}
