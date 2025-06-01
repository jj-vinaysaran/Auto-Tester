package com.Auto_Tester.Test_Automation.Mapper;

import com.Auto_Tester.Test_Automation.Model.Product;
import com.Auto_Tester.Test_Automation.DTO.ProductDTO;

public class ProductMapper {

    public static ProductDTO toDTO(Product product) {
        if (product == null) return null;
        ProductDTO dto = new ProductDTO();
        dto.setProductId(product.getProductId());
        dto.setProductName(product.getProductName());
        dto.setNumberOfCapabilities(product.getNumberOfCapabilities());
        return dto;
    }

    public static Product toEntity(ProductDTO dto) {
        if (dto == null) return null;
        Product product = new Product();
        product.setProductId(dto.getProductId());
        product.setProductName(dto.getProductName());
        product.setNumberOfCapabilities(dto.getNumberOfCapabilities());
        return product;
    }
}
