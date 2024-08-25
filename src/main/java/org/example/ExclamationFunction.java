package org.example;

import lombok.extern.slf4j.Slf4j;
import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

@Slf4j
public class ExclamationFunction implements Function<String, String> {
    @Override
    public String process(String s, Context context) throws Exception {
        log.info("Processing {}", s);
        return s + "!";
    }
}