package com.howtodoinjava.demo.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.howtodoinjava.demo.model.Product;
import com.howtodoinjava.demo.service.ProductService;

@RestController
public class ProductController {

    @Autowired
    private ProductService productService;

    @PostMapping("/addProducts")
    public String addProducts(@RequestBody List<Product> products) {
        Product product1 = new Product();
        product1.setName(products.get(0).getName());
        product1.setPrice(products.get(0).getPrice());

        Product product2 = new Product();
        product2.setName(products.get(1).getName());
        product2.setPrice(products.get(1).getPrice());

        try {
            productService.addProduct(product1, product2);
            return "Products added successfully";
        } catch (Exception e) {
            return "Failed to add products: " + e.getMessage();
        }
    }
}
