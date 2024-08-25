package org.example;

import org.apache.pulsar.client.api.Producer;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class MessageProducer {
    private final Producer producer;

    public MessageProducer(Producer producer) {
        this.producer = producer;
    }

    public void sendData(int countOfMessagesToSend ) throws IOException {
        for (int i = 0; i < countOfMessagesToSend; i++) {
            producer.send("Hello World " + i);
        }
    }

}
