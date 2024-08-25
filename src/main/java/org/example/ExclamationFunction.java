package org.example;

import org.apache.pulsar.functions.api.Context;
import org.apache.pulsar.functions.api.Function;

public class ExclamationFunction implements Function<String, String> {
    @Override
    public String process(String s, Context context) throws Exception {
        return s + "!";
    }
}