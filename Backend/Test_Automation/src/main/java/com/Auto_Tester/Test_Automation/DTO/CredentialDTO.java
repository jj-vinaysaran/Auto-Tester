package com.Auto_Tester.Test_Automation.DTO;

import lombok.Data;

@Data
public class CredentialDTO {
    
    private String credentialId;
    private String salesforceLoginURL;
    private String salesforceLoginPassword;
    private String environmentName;
    private String customerSignID;
    private String customerSignPassword;
    private String counterSignUserID;
    private String counterSignPassword;
}
