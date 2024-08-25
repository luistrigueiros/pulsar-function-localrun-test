package org.example;

import org.apache.pulsar.common.functions.FunctionConfig;
import org.apache.pulsar.functions.LocalRunner;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration
public class LocalRunnerConfiguration implements DisposableBean {

    private static LocalRunner localRunner;

    public LocalRunnerConfiguration(PulsarInfoDisplayer pulsarInfoDisplayer, FunctionConfig functionConfig) throws Exception {
        localRunner = LocalRunner.builder()
                .brokerServiceUrl(pulsarInfoDisplayer.getPulsarBrokerUrl())
                .functionConfig(functionConfig)
                .build();
        localRunner.start(false);
    }


    @Override
    public void destroy() throws Exception {
        localRunner.stop();
    }
}
