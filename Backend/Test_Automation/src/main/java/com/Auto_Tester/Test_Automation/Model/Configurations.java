package com.Auto_Tester.Test_Automation.Model;

import lombok.Data;
import java.util.List;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "configuration")
public class Configurations {

    @Id
    private String configurationId;
    private String productId;
    private String capabilityId;
    private String testCaseId;
    private int numberOfFields;
    private List<Field> fields;
    
}
