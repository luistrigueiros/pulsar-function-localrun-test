package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.client.api.*;
import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.functions.LocalRunner;

import java.io.IOException;
import java.util.Collections;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Slf4j
public class Main {

    public static final String BROKER_URL = "pulsar://localhost:43073";
    final static String IN = "persistent://public/default/raw-feed";
    final static String OUT = "persistent://public/default/filtered-feed";
    private static ExecutorService executor;
    private static LocalRunner localRunner;
    private static PulsarClient client;
    private static Producer<String> producer;
    private static Consumer<String> consumer;


    public static void main(String[] args) throws Exception {
        startLocalRunner();
        init();
        startConsumer();
        sendData();
        shutdown();
    }

    private static void startLocalRunner() throws Exception {
        localRunner = LocalRunner.builder()
                .brokerServiceUrl(BROKER_URL)
                .functionConfig(getFunctionConfig())
                .build();
        localRunner.start(false);
    }

    private static void init() throws PulsarClientException {
        executor = Executors.newFixedThreadPool(2);
        client = PulsarClient.builder()
                .serviceUrl(BROKER_URL)
                .build();

        producer = client.newProducer(Schema.STRING).topic(IN).create();
        consumer = client.newConsumer(Schema.STRING).topic(OUT).subscriptionName("validation-sub").subscribe();
    }

    private static FunctionConfig getFunctionConfig() {
        FunctionConfig functionConfig = new FunctionConfig();
        functionConfig.setName("exclamation");
        functionConfig.setInputs(Collections.singleton(IN));
        functionConfig.setClassName(ExclamationFunction.class.getName());
        functionConfig.setRuntime(FunctionConfig.Runtime.JAVA);
        functionConfig.setOutput(OUT);
        return functionConfig;
    }

    private static void startConsumer() {
        Runnable runnableTask = () -> {
            while (true) {
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

    private static void shutdown() throws Exception {
        Thread.sleep(30000);
        executor.shutdown();
        localRunner.stop();
        producer.close();
        consumer.close();
        System.exit(0);
    }

    private static void sendData() throws IOException {
        for (int i = 0; i < 10; i++) {
            producer.send("Hello World " + i);
        }
    }

}