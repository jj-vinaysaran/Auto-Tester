package com.Auto_Tester.Test_Automation.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "credentials")
public class Credentials {

    // User - Specific credentials used for Automation 
    @Id
    private String credentialId;
    private String salesforceLoginURL;
    private String salesforceLoginPassword;
    private String environmentName;
    private String customerSignID;
    private String customerSignPassword;
    private String counterSignUserID;
    private String counterSignPassword;
    
}
