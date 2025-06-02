package com.Auto_Tester.Test_Automation.Controller;

import com.Auto_Tester.Test_Automation.DTO.TestCasesDTO;
import com.Auto_Tester.Test_Automation.Model.TestCases;
import com.Auto_Tester.Test_Automation.Model.Capability;
import com.Auto_Tester.Test_Automation.Repository.TestCasesRepository;
import com.Auto_Tester.Test_Automation.Repository.CapabilityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/testcases")
public class TestCasesController {

    @Autowired
    private TestCasesRepository testCasesRepository;

    @Autowired
    private CapabilityRepository capabilityRepository;

    // Create Test Case (numberOfConfigurations default to 0, update capability's numberOfTestCases)
    @PostMapping
    public ResponseEntity<TestCasesDTO> createTestCase(@RequestBody TestCasesDTO testCaseDTO) {
        TestCases testCase = new TestCases();
        testCase.setTestCaseName(testCaseDTO.getTestCaseName());
        testCase.setProductId(testCaseDTO.getProductId());
        testCase.setCapabilityId(testCaseDTO.getCapabilityId());
        testCase.setNumberOfConfigurations(0); // default

        TestCases savedTestCase = testCasesRepository.save(testCase);

        // Update capability's numberOfTestCases
        Optional<Capability> capabilityOptional = capabilityRepository.findById(testCaseDTO.getCapabilityId());
        capabilityOptional.ifPresent(capability -> {
            capability.setNumberOfTestCases(capability.getNumberOfTestCases() + 1);
            capabilityRepository.save(capability);
        });

        testCaseDTO.setTestCaseId(savedTestCase.getTestCaseId());
        testCaseDTO.setNumberOfConfigurations(0); // default in response

        return ResponseEntity.ok(testCaseDTO);
    }

    // Get all Test Cases
    @GetMapping
    public ResponseEntity<List<TestCasesDTO>> getAllTestCases() {
        List<TestCasesDTO> testCases = testCasesRepository.findAll().stream()
                .map(testCase -> {
                    TestCasesDTO dto = new TestCasesDTO();
                    dto.setTestCaseId(testCase.getTestCaseId());
                    dto.setTestCaseName(testCase.getTestCaseName());
                    dto.setProductId(testCase.getProductId());
                    dto.setCapabilityId(testCase.getCapabilityId());
                    dto.setNumberOfConfigurations(testCase.getNumberOfConfigurations());
                    return dto;
                })
                .collect(Collectors.toList());
        return ResponseEntity.ok(testCases);
    }

    // Get Test Case by ID
    @GetMapping("/{id}")
    public ResponseEntity<TestCasesDTO> getTestCaseById(@PathVariable String id) {
        Optional<TestCases> testCaseOptional = testCasesRepository.findById(id);
        if (testCaseOptional.isPresent()) {
            TestCases testCase = testCaseOptional.get();
            TestCasesDTO dto = new TestCasesDTO();
            dto.setTestCaseId(testCase.getTestCaseId());
            dto.setTestCaseName(testCase.getTestCaseName());
            dto.setProductId(testCase.getProductId());
            dto.setCapabilityId(testCase.getCapabilityId());
            dto.setNumberOfConfigurations(testCase.getNumberOfConfigurations());
            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Update Test Case (only testCaseName for now)
    @PutMapping("/{id}")
    public ResponseEntity<TestCasesDTO> updateTestCase(@PathVariable String id, @RequestBody TestCasesDTO testCaseDTO) {
        Optional<TestCases> testCaseOptional = testCasesRepository.findById(id);
        if (testCaseOptional.isPresent()) {
            TestCases testCase = testCaseOptional.get();
            testCase.setTestCaseName(testCaseDTO.getTestCaseName());
            TestCases updatedTestCase = testCasesRepository.save(testCase);

            testCaseDTO.setTestCaseId(updatedTestCase.getTestCaseId());
            testCaseDTO.setProductId(updatedTestCase.getProductId());
            testCaseDTO.setCapabilityId(updatedTestCase.getCapabilityId());
            testCaseDTO.setNumberOfConfigurations(updatedTestCase.getNumberOfConfigurations());

            return ResponseEntity.ok(testCaseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Test Case (and decrement capability's numberOfTestCases)
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTestCase(@PathVariable String id) {
        Optional<TestCases> testCaseOptional = testCasesRepository.findById(id);
        if (testCaseOptional.isPresent()) {
            TestCases testCase = testCaseOptional.get();
            String capabilityId = testCase.getCapabilityId();
            testCasesRepository.deleteById(id);

            // Decrement numberOfTestCases in Capability
            Optional<Capability> capabilityOptional = capabilityRepository.findById(capabilityId);
            capabilityOptional.ifPresent(capability -> {
                int currentCount = capability.getNumberOfTestCases();
                capability.setNumberOfTestCases(Math.max(0, currentCount - 1));
                capabilityRepository.save(capability);
            });

            return ResponseEntity.ok("Test case deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
