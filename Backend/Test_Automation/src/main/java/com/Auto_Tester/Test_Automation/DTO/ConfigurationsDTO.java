package com.Auto_Tester.Test_Automation.DTO;

import lombok.Data;
import java.util.List;

@Data
public class ConfigurationsDTO {
    
    private String configurationId;
    private String productId;
    private String capabilityId;
    private String testCaseId;
    private String configName;
    private int numberOfFields;
    private List<FieldDTO> fields;
}
