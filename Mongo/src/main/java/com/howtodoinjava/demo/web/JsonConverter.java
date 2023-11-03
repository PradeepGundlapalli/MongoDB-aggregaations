package com.howtodoinjava.demo.web;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonConverter {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T convertJsonToObject(String jsonString, Class<T> targetClass) throws Exception {
        return objectMapper.readValue(jsonString, targetClass);
    }
}