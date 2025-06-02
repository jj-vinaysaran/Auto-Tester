package com.Auto_Tester.Test_Automation.Repository;

import org.springframework.stereotype.Repository;
import com.Auto_Tester.Test_Automation.Model.Capability;
import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface CapabilityRepository extends MongoRepository<Capability,String>{

}
