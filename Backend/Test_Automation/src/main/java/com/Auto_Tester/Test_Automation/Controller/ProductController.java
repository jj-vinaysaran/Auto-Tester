package com.Auto_Tester.Test_Automation.Controller;

import com.Auto_Tester.Test_Automation.DTO.ProductDTO;
import com.Auto_Tester.Test_Automation.Model.Product;
import com.Auto_Tester.Test_Automation.Repository.ProductRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/products")
public class ProductController {

    @Autowired
    private ProductRepository productRepository;

    @PostMapping
    public ResponseEntity<?> createProduct(@RequestBody ProductDTO productDTO) {
        String productId = productDTO.getProductId();
        String productName = productDTO.getProductName();

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName(productName);
        product.setNumberOfCapabilities(0);  

        Product savedProduct = productRepository.save(product);

        productDTO.setProductId(savedProduct.getProductId());
        productDTO.setNumberOfCapabilities(0);

        return ResponseEntity.ok(productDTO);
    }


    @GetMapping
    public ResponseEntity<List<ProductDTO>> getAllProducts() {
        List<ProductDTO> products = productRepository.findAll().stream()
                .map(product -> {
                    ProductDTO dto = new ProductDTO();
                    dto.setProductId(product.getProductId());
                    dto.setProductName(product.getProductName());
                    dto.setNumberOfCapabilities(product.getNumberOfCapabilities());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProductById(@PathVariable String id) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            ProductDTO dto = new ProductDTO();
            dto.setProductId(product.getProductId());
            dto.setProductName(product.getProductName());
            dto.setNumberOfCapabilities(product.getNumberOfCapabilities());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable String id, @RequestBody ProductDTO productDTO) {
        Optional<Product> productOptional = productRepository.findById(id);
        if (productOptional.isPresent()) {
            Product product = productOptional.get();
            product.setProductName(productDTO.getProductName());
            Product updatedProduct = productRepository.save(product);

            productDTO.setProductId(updatedProduct.getProductId());
            productDTO.setNumberOfCapabilities(updatedProduct.getNumberOfCapabilities());
            return ResponseEntity.ok(productDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        Optional<Product> productOptional = productRepository.findById(id);

        if (productOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Product product = productOptional.get();

        if (product.getNumberOfCapabilities() > 0) {
            return ResponseEntity.badRequest()
                .body("Cannot delete product because it has associated capabilities.");
        }

        productRepository.deleteById(id);
        return ResponseEntity.ok("Product deleted successfully!");
    }

}
