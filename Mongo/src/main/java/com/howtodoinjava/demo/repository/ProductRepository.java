package com.howtodoinjava.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.howtodoinjava.demo.model.Product;

public interface ProductRepository extends MongoRepository<Product, String> {
}
