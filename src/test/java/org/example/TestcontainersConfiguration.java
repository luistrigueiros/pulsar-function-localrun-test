package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PulsarContainer;
import org.testcontainers.utility.DockerImageName;

@Slf4j
@TestConfiguration(proxyBeanMethods = false)
public class TestcontainersConfiguration {
    @Bean
    @ServiceConnection
    PulsarContainer pulsarContainer() {
        return new PulsarContainer(DockerImageName.parse("apachepulsar/pulsar:latest"))
                .withFunctionsWorker()
                .withReuse(true);
    }
}
