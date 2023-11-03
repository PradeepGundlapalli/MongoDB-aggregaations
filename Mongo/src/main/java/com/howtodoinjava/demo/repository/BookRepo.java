package com.howtodoinjava.demo.repository;

//Java Program to Illustrate BookRepo File

 
import org.springframework.data.mongodb.repository.MongoRepository;

import com.howtodoinjava.demo.model.Book;

 

public interface BookRepo
	extends MongoRepository<Book, Integer> {
}
