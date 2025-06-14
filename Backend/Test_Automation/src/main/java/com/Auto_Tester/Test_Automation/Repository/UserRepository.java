package com.Auto_Tester.Test_Automation.Repository;

import com.Auto_Tester.Test_Automation.Model.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

    User findByUserName(String userName);
}
