package com.Auto_Tester.Test_Automation.Model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "users")
public class User {
    @Id
    // Platform Login 
    private String userId;
    private String username;
    private String password;
}
