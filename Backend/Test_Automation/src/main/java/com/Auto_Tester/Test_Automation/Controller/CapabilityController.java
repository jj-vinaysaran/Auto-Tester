package com.Auto_Tester.Test_Automation.Controller;

import com.Auto_Tester.Test_Automation.DTO.CapabilityDTO;
import com.Auto_Tester.Test_Automation.Model.Capability;
import com.Auto_Tester.Test_Automation.Model.Product;
import com.Auto_Tester.Test_Automation.Repository.CapabilityRepository;
import com.Auto_Tester.Test_Automation.Repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/capabilities")
public class CapabilityController {

    @Autowired
    private CapabilityRepository capabilityRepository;

    @Autowired
    private ProductRepository productRepository;

    // Create Capability (numberOfTestCases default to 0, update product's numberOfCapabilities)
    @PostMapping
    public ResponseEntity<CapabilityDTO> createCapability(@RequestBody CapabilityDTO capabilityDTO) {
        Capability capability = new Capability();
        capability.setCapabilityName(capabilityDTO.getCapabilityName());
        capability.setProductId(capabilityDTO.getProductId());
        capability.setNumberOfTestCases(0); // default

        Capability savedCapability = capabilityRepository.save(capability);

        // Update product's numberOfCapabilities
        Optional<Product> productOptional = productRepository.findById(capabilityDTO.getProductId());
        productOptional.ifPresent(product -> {
            product.setNumberOfCapabilities(product.getNumberOfCapabilities() + 1);
            productRepository.save(product);
        });

        capabilityDTO.setCapabilityId(savedCapability.getCapabilityId());
        capabilityDTO.setNumberOfTestCases(0); // default in response

        return ResponseEntity.ok(capabilityDTO);
    }

    // Get all Capabilities
    @GetMapping
    public ResponseEntity<List<CapabilityDTO>> getAllCapabilities() {
        List<CapabilityDTO> capabilities = capabilityRepository.findAll().stream()
                .map(capability -> {
                    CapabilityDTO dto = new CapabilityDTO();
                    dto.setCapabilityId(capability.getCapabilityId());
                    dto.setCapabilityName(capability.getCapabilityName());
                    dto.setProductId(capability.getProductId());
                    dto.setNumberOfTestCases(capability.getNumberOfTestCases());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(capabilities);
    }

    // Get Capability by ID
    @GetMapping("/{id}")
    public ResponseEntity<CapabilityDTO> getCapabilityById(@PathVariable String id) {
        Optional<Capability> capabilityOptional = capabilityRepository.findById(id);
        if (capabilityOptional.isPresent()) {
            Capability capability = capabilityOptional.get();
            CapabilityDTO dto = new CapabilityDTO();
            dto.setCapabilityId(capability.getCapabilityId());
            dto.setCapabilityName(capability.getCapabilityName());
            dto.setProductId(capability.getProductId());
            dto.setNumberOfTestCases(capability.getNumberOfTestCases());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update Capability (only capabilityName, productId won't be changed here)
    @PutMapping("/{id}")
    public ResponseEntity<CapabilityDTO> updateCapability(@PathVariable String id, @RequestBody CapabilityDTO capabilityDTO) {
        Optional<Capability> capabilityOptional = capabilityRepository.findById(id);
        if (capabilityOptional.isPresent()) {
            Capability capability = capabilityOptional.get();
            capability.setCapabilityName(capabilityDTO.getCapabilityName());
            Capability updatedCapability = capabilityRepository.save(capability);

            capabilityDTO.setCapabilityId(updatedCapability.getCapabilityId());
            capabilityDTO.setProductId(updatedCapability.getProductId());
            capabilityDTO.setNumberOfTestCases(updatedCapability.getNumberOfTestCases());

            return ResponseEntity.ok(capabilityDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Capability (and decrement product's numberOfCapabilities)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCapability(@PathVariable String id) {
        Optional<Capability> capabilityOptional = capabilityRepository.findById(id);
        if (capabilityOptional.isPresent()) {
            Capability capability = capabilityOptional.get();
            String productId = capability.getProductId();
            capabilityRepository.deleteById(id);

            // Decrement numberOfCapabilities in Product
            Optional<Product> productOptional = productRepository.findById(productId);
            productOptional.ifPresent(product -> {
                int currentCount = product.getNumberOfCapabilities();
                product.setNumberOfCapabilities(Math.max(0, currentCount - 1));
                productRepository.save(product);
            });

            return ResponseEntity.ok("Capability deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
