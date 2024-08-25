package org.example;

import org.apache.pulsar.client.api.Consumer;
import org.apache.pulsar.client.api.Message;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

@Component
public class MessageConsumer implements DisposableBean {
    private final Consumer<String> consumer;
    private final AtomicInteger counter = new AtomicInteger(0);
    private ExecutorService executor;

    public MessageConsumer(Consumer<String> consumer) {
        this.consumer = consumer;
    }

    void consumeMessages(int countOfMessageToConsume) {
        executor = Executors.newFixedThreadPool(2);
        Runnable runnableTask = () -> {
            while (counter.incrementAndGet() < countOfMessageToConsume) {
                Message<String> msg = null;
                try {
                    msg = consumer.receive();
                    System.out.printf("Message received: %s \n", msg.getValue());
                    consumer.acknowledge(msg);
                } catch (Exception e) {
                    consumer.negativeAcknowledge(msg);
                }
            }
        };
        executor.execute(runnableTask);
    }

    @Override
    public void destroy() throws Exception {
        executor.shutdown();
    }
}
