package org.example.function;

import org.apache.pulsar.common.functions.FunctionConfig;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;


@Configuration
public class FunctionConfigConfiguration {
    @Value("${app.input}")
    private String inputTopic;
    @Value("${app.output}")
    private String outputTopic;

    @Bean
    FunctionConfig getFunctionConfig() {
        FunctionConfig functionConfig = new FunctionConfig();
        functionConfig.setName("exclamation");
        functionConfig.setInputs(Collections.singleton(inputTopic));
        functionConfig.setClassName(ExclamationFunction.class.getName());
        functionConfig.setRuntime(FunctionConfig.Runtime.JAVA);
        functionConfig.setOutput(outputTopic);
        return functionConfig;
    }

}
