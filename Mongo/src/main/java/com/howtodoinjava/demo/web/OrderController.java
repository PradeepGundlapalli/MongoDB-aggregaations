package com.howtodoinjava.demo.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.howtodoinjava.demo.model.Book;
import com.howtodoinjava.demo.model.Order;
import com.howtodoinjava.demo.repository.BookRepo;
import com.howtodoinjava.demo.repository.OrderRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;

@RestController
public class OrderController {
    private final MongoTemplate mongoTemplate;
    private final OrderRepository orderRepository;

    @Autowired
    public OrderController(MongoTemplate mongoTemplate, OrderRepository orderRepository) {
        this.mongoTemplate = mongoTemplate;
        this.orderRepository = orderRepository;
    }
    @Autowired
	private OrderRepository repo;

    @GetMapping("/group-by")
    public List<Object> groupByTwoFields(
            @RequestParam(name = "groupByField1") String groupByField1,
            @RequestParam(name = "groupByField2") String groupByField2,
            @RequestParam(name = "groupByField3") String groupByField3) throws JsonParseException, JsonMappingException, IOException {

        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.group(groupByField1, groupByField2,groupByField3)
                ,Aggregation.project(groupByField1, groupByField2, groupByField3) 
        );

        AggregationResults<Object> result = mongoTemplate.aggregate(
                aggregation, "orders", Object.class);
        
      

        List<Object> s=result.getMappedResults();
         String[] a= {"a"};
         System.out.println(a[0]);
        
        List<Order> l=new ArrayList<>();
        Order o=new Order();
         
        for(Object s1:s)
        {
        	LinkedHashMap<String, String> o1= (LinkedHashMap<String, String>) s.get(0);
        
        	ObjectMapper mapper=new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
       	 String json = mapper.writeValueAsString(o1);
       
        	 o=mapper.readValue(json, Order.class);	
        	 l.add(o);
        	System.out.println(o);
        	
        }
        
        
 
        return s;
    }
    
    @PostMapping("/addOrder")
	public String saveOrder(@RequestBody Order order) {
		repo.save(order);

		return "Added Successfully";
	}
    
}

