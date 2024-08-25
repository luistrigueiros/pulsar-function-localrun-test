package org.example;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class Main implements CommandLineRunner {
    @Autowired
    private MessageProducer producer;
    @Autowired
    private MessageConsumer consumer;

    public static void main(String[] args) throws Exception {
        SpringApplication.run(Main.class, args);
    }


    @Override
    public void run(String... args) throws Exception {
        int countOfMessagesToSend = 10;
        producer.sendData(countOfMessagesToSend);
        consumer.consumeMessages(countOfMessagesToSend);
    }
}