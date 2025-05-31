package com.Auto_Tester.Test_Automation.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "products")
public class Product {
    @Id
    private String productId;
    private String productName;
    private int numberOfCapabilities;
}

