package org.example;

import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Configuration;
import org.testcontainers.containers.PulsarContainer;

@Slf4j
@TestConfiguration
public class PulsarInfoDisplayer {
    private final PulsarContainer pulsarContainer;

    public PulsarInfoDisplayer(PulsarContainer pulsarContainer) {
        this.pulsarContainer = pulsarContainer;
    }

    @PostConstruct
    void postInitialize() {
        log.info("Pulsar container started");
        log.info( "HttpServiceUrl {}", pulsarContainer.getHttpServiceUrl());
        log.info( "PulsarBrokerUrl {}", pulsarContainer.getPulsarBrokerUrl());
    }

    public String getPulsarUrl() {
        return pulsarContainer.getHttpServiceUrl();
    }

    public String getPulsarBrokerUrl() {
        return pulsarContainer.getPulsarBrokerUrl();
    }
}
