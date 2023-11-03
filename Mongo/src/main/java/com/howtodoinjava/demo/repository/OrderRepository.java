package com.howtodoinjava.demo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import com.howtodoinjava.demo.model.Order;

import java.util.List;

public interface OrderRepository extends MongoRepository<Order, String> {
    // Define custom queries here if needed

    // Custom query to perform dynamic grouping by two fields
    @Query(value = "{}", fields = "{ ?0 : 1, ?1 : 1 }")
    List<Object> groupByTwoFields(String groupByField1, String groupByField2);
}
