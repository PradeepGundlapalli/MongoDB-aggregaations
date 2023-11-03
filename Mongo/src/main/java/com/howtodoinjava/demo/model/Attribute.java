package com.howtodoinjava.demo.model;

import java.util.Date;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attribute{
    int id;
    
    int orderId;
    String status;
    List<String> quantities;
    //Getters & Setters 
}