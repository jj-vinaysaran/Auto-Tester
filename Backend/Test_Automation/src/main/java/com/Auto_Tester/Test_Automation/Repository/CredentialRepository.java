package com.Auto_Tester.Test_Automation.Repository;

import org.springframework.stereotype.Repository;
import com.Auto_Tester.Test_Automation.Model.Credentials;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CredentialRepository extends MongoRepository<Credentials, String> {
    
}
