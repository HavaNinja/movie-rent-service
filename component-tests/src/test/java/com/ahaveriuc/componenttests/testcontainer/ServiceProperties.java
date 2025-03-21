package com.ahaveriuc.componenttests.testcontainer;

import com.playtika.testcontainer.common.properties.CommonContainerProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("embedded.service")
@Getter
@Setter
class ServiceProperties extends CommonContainerProperties {
    private String dockerImage;
    protected String defaultDockerImage;
    private Integer port = 8080;
    private Integer managementPort = 8081;
    private String healthPath;
}
