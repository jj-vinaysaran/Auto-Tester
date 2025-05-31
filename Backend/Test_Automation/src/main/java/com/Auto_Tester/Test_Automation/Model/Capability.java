package com.Auto_Tester.Test_Automation.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "capability")
public class Capability{

    @Id
    private String capabilityId;
    private String productId;
    private String capabilityName;
    private int numberOfTestCases;
}
