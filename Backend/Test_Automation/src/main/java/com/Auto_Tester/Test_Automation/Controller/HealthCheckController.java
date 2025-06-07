package com.Auto_Tester.Test_Automation.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HealthCheckController {

    @Autowired
    private MongoTemplate mongoTemplate;

    @GetMapping("/status")
    public String checkStatus() {
        try {
            mongoTemplate.getDb().getName(); // Triggers a connection test
            return " MongoDB is connected!";
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return "MongoDB connection failed: " + e.getMessage();
        }
    }
}

