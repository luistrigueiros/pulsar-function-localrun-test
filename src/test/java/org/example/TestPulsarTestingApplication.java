package org.example;

import org.springframework.boot.SpringApplication;

public class TestPulsarTestingApplication {

    public static void main(String[] args) {
        SpringApplication.from(Main::main)
                .with(TestcontainersConfiguration.class, PulsarInfoDisplayer.class, ProducerConsumerConfiguration.class, LocalRunnerConfiguration.class)
                .run(args);
    }

}
