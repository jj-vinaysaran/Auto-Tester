package com.Auto_Tester.Test_Automation.Repository;


import com.Auto_Tester.Test_Automation.Model.Configurations;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ConfigurationsRepository extends MongoRepository<Configurations, String>{
    
}
