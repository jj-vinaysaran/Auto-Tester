package com.Auto_Tester.Test_Automation.DTO;

import lombok.Data;

@Data
public class CapabilityDTO {
    
    private String capabilityId;
    private String productId;
    private String capabilityName;
    private int numberOfTestCases;
}
