package org.example;

import org.apache.pulsar.client.api.*;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

@TestConfiguration
public class ProducerConsumerConfiguration implements DisposableBean {
    private PulsarClient client;
    private Producer<String> producer;
    private Consumer<String> consumer;

    public ProducerConsumerConfiguration(
            PulsarInfoDisplayer pulsarInfoDisplayer,
            @Value("${app.input}")
            String inputTopic,
            @Value("${app.output}")
            String outputTopic) throws PulsarClientException {
        this.client = PulsarClient.builder()
                .serviceUrl(pulsarInfoDisplayer.getPulsarBrokerUrl())
                .build();

        this.producer = client.newProducer(Schema.STRING).topic(inputTopic).create();
        this.consumer = client.newConsumer(Schema.STRING)
                .topic(outputTopic)
                .subscriptionName("validation-sub")
                .subscribe();
    }

    @Override
    public void destroy() throws Exception {
        producer.close();
        consumer.close();
        client.close();
    }

    @Bean
    public Producer<String> getProducer() {
        return producer;
    }

    @Bean
    public Consumer<String> getConsumer() {
        return consumer;
    }
}
