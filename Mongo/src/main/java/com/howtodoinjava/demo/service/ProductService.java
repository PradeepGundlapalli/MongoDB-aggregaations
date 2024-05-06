package com.howtodoinjava.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import com.howtodoinjava.demo.model.Product;
import com.howtodoinjava.demo.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private ProductRepository productRepository;

    public void addProduct(Product product1, Product product2) {
        try {
            mongoTemplate.save(product1);
            mongoTemplate.save(product2);
            if (product1.getName().equals("Invalid")) {
                throw new RuntimeException("Simulating failure - product1 is invalid");
            }
        } catch (Exception e) {
            // If any exception occurs, rollback by deleting the products
            mongoTemplate.remove(product1);
            mongoTemplate.remove(product2);
            throw e;
        }
    }
}
