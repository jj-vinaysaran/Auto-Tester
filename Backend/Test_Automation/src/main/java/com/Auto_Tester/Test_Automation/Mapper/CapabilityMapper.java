package com.Auto_Tester.Test_Automation.Mapper;

import com.Auto_Tester.Test_Automation.Model.Capability;
import com.Auto_Tester.Test_Automation.DTO.CapabilityDTO;

public class CapabilityMapper {

    public static CapabilityDTO toDTO(Capability capability) {
        if (capability == null) return null;
        CapabilityDTO dto = new CapabilityDTO();
        dto.setCapabilityId(capability.getCapabilityId());
        dto.setCapabilityName(capability.getCapabilityName());
        dto.setProductId(dto.getProductId());
        dto.setNumberOfTestCases(capability.getNumberOfTestCases());
        return dto;
    }

    public static Capability toEntity(CapabilityDTO dto) {
        if (dto == null) return null;
        Capability capability = new Capability();
        capability.setCapabilityId(dto.getCapabilityId());
        capability.setCapabilityName(dto.getCapabilityName());
        capability.setProductId(dto.getProductId());
        capability.setNumberOfTestCases(dto.getNumberOfTestCases());
        return capability;
    }
    
}
