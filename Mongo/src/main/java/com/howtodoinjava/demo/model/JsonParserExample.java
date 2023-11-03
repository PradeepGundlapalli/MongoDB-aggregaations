package com.howtodoinjava.demo.model;

import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonParserExample {
    public static void main(String[] args) throws Exception {
        String jsonString = "{\"id\":1,\"name\":\"John Doe\",\"email\":\"john@example.com\"," +
                "\"address\":{\"city\":\"New York\"}}";

        // Create ObjectMapper
        ObjectMapper objectMapper = new ObjectMapper();

        // Parse JSON into Person class
        Person person = objectMapper.readValue(jsonString, Person.class);

        // Access the parsed data
        System.out.println("ID: " + person.getId());
        System.out.println("Name: " + person.getName());
        System.out.println("Email: " + person.getEmail());

        Address address = person.getAddress();
        System.out.println("Street: " + address.getStreet());
        System.out.println("City: " + address.getCity());

        List<String> phoneNumbers = person.getPhoneNumbers();
        System.out.println("Phone Numbers:");
        for (String phoneNumber : phoneNumbers) {
            System.out.println(phoneNumber);
        }
    }
}
 



