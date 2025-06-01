package com.Auto_Tester.Test_Automation.Repository;

import com.Auto_Tester.Test_Automation.Model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository  extends MongoRepository<Product,String> {
    Product findProductBy(String productName);

}

