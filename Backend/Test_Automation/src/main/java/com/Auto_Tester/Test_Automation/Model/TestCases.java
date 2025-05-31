package com.Auto_Tester.Test_Automation.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "testcases")
public class TestCases {

    @Id
    private String testCaseId;
    private String productId;
    private String capabilityId;
    private String testCaseName;
    private String numberOfConfigurations;
}
