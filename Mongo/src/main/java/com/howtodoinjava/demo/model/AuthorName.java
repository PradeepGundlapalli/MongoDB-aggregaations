package com.howtodoinjava.demo.model;

import java.util.List;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Annotations
@Data
@NoArgsConstructor 

public class AuthorName {

	@Id 
	private Integer _id;
	private String name;
	private int age;
	private PName fullname;
}
