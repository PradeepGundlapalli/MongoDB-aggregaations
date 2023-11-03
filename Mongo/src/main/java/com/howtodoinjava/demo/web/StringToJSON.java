package com.howtodoinjava.demo.web;

import org.json.simple.JSONObject;

public class StringToJSON {
    public static void main(String[] args) {
        String inputString = "authorName.fullname.fname";

        // Split the input string by '.'
        String[] parts = inputString.split("\\.");

        // Create a JSON-like structure
        JSONObject result = new JSONObject();
        JSONObject currentObject = result;

        for (int i = 0; i < parts.length; i++) {
            String part = parts[i];

            if (i == parts.length - 1) {
                // Last part should be an empty string as a placeholder
                currentObject.put(part, "");
            } else {
                // Create a nested JSONObject
                JSONObject nestedObject = new JSONObject();
                currentObject.put(part, nestedObject);
                currentObject = nestedObject;
            }
        }

        String s=result.toJSONString();
        System.out.println();
    }
}
