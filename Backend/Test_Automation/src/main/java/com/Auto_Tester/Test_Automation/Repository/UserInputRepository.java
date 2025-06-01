package com.Auto_Tester.Test_Automation.Repository;

import com.Auto_Tester.Test_Automation.Model.UserInput;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserInputRepository extends MongoRepository<UserInput, String> {
}

