package com.ahaveriuc.testcontainer;

import com.github.dockerjava.api.DockerClient;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnExpression;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MapPropertySource;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import java.util.Map;

import static com.playtika.testcontainer.common.utils.ContainerUtils.configureCommonsAndStart;
import static java.util.Map.entry;
import static org.testcontainers.containers.wait.strategy.Wait.forHttp;


@Slf4j
@Configuration
@ConditionalOnExpression("${embedded.containers.enabled:true}")
@ConditionalOnProperty(
        name = "embedded.service.enabled",
        havingValue = "true",
        matchIfMissing = true
)
@EnableConfigurationProperties({ServiceProperties.class})
public class EmbeddedServiceBootstrapConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger("Container");

    @Bean(destroyMethod = "stop")
    ServiceContainer service(ConfigurableEnvironment environment, ServiceProperties properties) {
        ServiceContainer service = new ServiceContainer(properties.getDockerImage())
                .withExposedPorts(properties.getPort(), properties.getManagementPort())
                .withLogConsumer(new Slf4jLogConsumer(LOGGER))
                .waitingFor(forHttp(properties.getHealthPath()).forPort(properties.getManagementPort()).forStatusCode(200));

        service = (ServiceContainer) configureCommonsAndStart(service, properties, log);

        registerEnvironment(service, environment, properties);

        return service;
    }

    private void registerEnvironment(ServiceContainer service, ConfigurableEnvironment environment, ServiceProperties properties) {
        String host = service.getHost();
        Integer mappedPort = service.getMappedPort(properties.getPort());
        Integer managementPort = service.getMappedPort(properties.getManagementPort());

        Map<String, Object> params = Map.ofEntries(
                entry("embedded.service.host", host),
                entry("embedded.service.port", mappedPort),
                entry("embedded.service.management.port", managementPort));

        log.info("Started {}.Connection details :{}, Connection URI http://{}:{}", properties.getDockerImage(), params, host, mappedPort);

        MapPropertySource propertySource = new MapPropertySource("embeddedServiceInfo", params);
        environment.getPropertySources().addFirst(propertySource);
    }

    private static class ServiceContainer extends GenericContainer<ServiceContainer> {
        public ServiceContainer(String dockerImageName) {
            super(dockerImageName);
        }

        @Override
        public void stop() {
            super.stop();
            String dockerImageName = getDockerImageName();

            DockerClient dockerClient = DockerClientFactory.lazyClient();

            dockerClient.removeImageCmd(dockerImageName).exec();

            log.info("image {} was successfully removed", dockerImageName);
        }
    }

}
