package com.Auto_Tester.Test_Automation.DTO;

import lombok.Data;

@Data
public class TestCasesDTO{

    private String testCaseId;
    private String productId;
    private String capabilityId;
    private String testCaseName;
    private int numberOfConfigurations;
    
}
