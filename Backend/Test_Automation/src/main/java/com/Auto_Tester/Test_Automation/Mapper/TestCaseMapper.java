package com.Auto_Tester.Test_Automation.Mapper;

import com.Auto_Tester.Test_Automation.DTO.TestCasesDTO;
import com.Auto_Tester.Test_Automation.Model.TestCases;

public class TestCaseMapper {

    public static TestCasesDTO toDTO(TestCases testCase) {
        if (testCase == null) return null;

        TestCasesDTO dto = new TestCasesDTO();
        dto.setTestCaseId(testCase.getTestCaseId());
        dto.setProductId(testCase.getProductId());
        dto.setCapabilityId(testCase.getCapabilityId());
        dto.setTestCaseName(testCase.getTestCaseName());
        dto.setNumberOfConfigurations(testCase.getNumberOfConfigurations());
        return dto;
    }

    public static TestCases toEntity(TestCasesDTO dto){
        if (dto == null) return null;

        TestCases testCase = new TestCases();
        testCase.setTestCaseId(dto.getTestCaseId());
        testCase.setProductId(dto.getProductId());
        testCase.setCapabilityId(dto.getCapabilityId());
        testCase.setTestCaseName(dto.getTestCaseName());
        testCase.setNumberOfConfigurations(dto.getNumberOfConfigurations());
        return testCase;
    }
}

