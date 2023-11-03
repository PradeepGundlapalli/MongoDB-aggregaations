package com.howtodoinjava.demo.model;

import org.springframework.data.annotation.Id;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//Annotations
@Data
 
public class PName {
	
	 

	@Id 
	private Integer _id;
	private String fname;
	private String lname;
}
