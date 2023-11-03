package com.howtodoinjava.demo.model;

//Java Program to illustrate Book File

//Importing required classes
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

//Annotations
@Data
 
@Document(collection = "Book")

//Class
public class Book
{

	// Attributes
	@Id 
	private int id;
	private String bookName;
	private String createdDate;
	//private AuthorName authorName;
	//private String cost;
	//
}
