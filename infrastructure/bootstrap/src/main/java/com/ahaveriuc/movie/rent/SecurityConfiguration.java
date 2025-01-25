package com.ahaveriuc.movie.rent;

import org.springframework.boot.autoconfigure.security.oauth2.resource.OAuth2ResourceServerProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtDecoders;
import org.springframework.security.oauth2.server.resource.web.BearerTokenAuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@PropertySource("classpath:security-oauth2-server.properties")
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity httpSecurity, OAuth2ResourceServerExtProperties properties) throws Exception {
        BearerTokenAuthenticationEntryPoint authenticationEntryPoint = new BearerTokenAuthenticationEntryPoint();
        authenticationEntryPoint.setRealmName(properties.getJwt().getRealm());

        return httpSecurity
                .formLogin(AbstractHttpConfigurer::disable)
                .logout(AbstractHttpConfigurer::disable)
                .rememberMe(AbstractHttpConfigurer::disable)
                .httpBasic(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(AbstractHttpConfigurer::disable)
                .sessionManagement(AbstractHttpConfigurer::disable)
                .authorizeHttpRequests(httpRequests -> httpRequests.requestMatchers("/health").permitAll()
                        .anyRequest().authenticated())
                .oauth2ResourceServer(resourceServer -> resourceServer.authenticationEntryPoint(authenticationEntryPoint)
                        .jwt(Customizer.withDefaults()))
                .build();
    }

    @Bean
    public JwtDecoder jwtDecoder(OAuth2ResourceServerExtProperties properties) {
        return JwtDecoders.fromIssuerLocation(properties.getJwt().getIssuerUri());
    }

    @Bean
    OAuth2ResourceServerExtProperties auth2ResourceServerExtProperties() {
        return new OAuth2ResourceServerExtProperties();
    }

    @ConfigurationProperties(prefix = "spring.security.oauth2.resourceserver")
    public static class OAuth2ResourceServerExtProperties extends OAuth2ResourceServerProperties {
        private final JwtExt jwt = new JwtExt();

        public JwtExt getJwt() {
            return this.jwt;
        }

        public static class JwtExt extends Jwt {
            private String realm;

            public String getRealm() {
                return this.realm;
            }

            public void setRealm(String realm) {
                this.realm = realm;
            }
        }
    }
}
