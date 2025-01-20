package com.ahaveriuc;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;


@Configuration
@EntityScan(basePackages = "com.ahaveriuc")
@EnableJpaRepositories(basePackages = "com.ahaveriuc")
public class PersistenceTestConfig {
}
