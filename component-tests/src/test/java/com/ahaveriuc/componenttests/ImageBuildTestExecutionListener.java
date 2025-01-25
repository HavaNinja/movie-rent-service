package com.ahaveriuc.componenttests;

import lombok.SneakyThrows;
import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.springframework.boot.ConfigurableBootstrapContext;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

import java.io.File;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

@Order(Ordered.HIGHEST_PRECEDENCE)
public class ImageBuildTestExecutionListener implements SpringApplicationRunListener {

    private final boolean buildImage;

    public ImageBuildTestExecutionListener(String[] args) {
        buildImage = Arrays.asList(args).contains("build.image=true");
    }

    @SneakyThrows
    @Override
    public void starting(ConfigurableBootstrapContext configurableBootstrapContext) {
        if (buildImage) {
            DefaultInvoker invoker = new DefaultInvoker();

            DefaultInvocationRequest request = new DefaultInvocationRequest();
            request.setPomFile(new File("./../pom.xml"));
            request.setGoals(List.of("clean package -DskipTests -DjibAllowInsecureRegistries -Dsha1=00000000"));
            request.setMavenHome(Path.of(".").toFile());
            invoker.setMavenExecutable(new File("./mvnw"));

            var result = invoker.execute(request);

            if (result.getExitCode() != 0) {
                if (result.getExecutionException() != null) {
                    throw new IllegalStateException("Build failed. Exit code: " + result.getExitCode(), result.getExecutionException());
                } else {
                    throw new IllegalStateException("Build failed. Exit code: " + result.getExitCode());
                }
            }
        }
    }
}
