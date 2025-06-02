package com.Auto_Tester.Test_Automation.Mapper;

import com.Auto_Tester.Test_Automation.DTO.ConfigurationsDTO;
import com.Auto_Tester.Test_Automation.Model.Configurations;
import java.util.stream.Collectors;

public class ConfigurationMapper {

    public static ConfigurationsDTO toDTO(Configurations config){
        if (config == null) return null;

        ConfigurationsDTO dto = new ConfigurationsDTO();
        dto.setConfigurationId(config.getConfigurationId());
        dto.setProductId(config.getProductId());
        dto.setCapabilityId(config.getCapabilityId());
        dto.setTestCaseId(config.getTestCaseId());
        dto.setConfigName(config.getConfigName());
        dto.setNumberOfFields(config.getNumberOfFields());

        // Map fields
        if (config.getFields() != null) {
            dto.setFields(config.getFields().stream()
                    .map(FieldMapper::toDTO)
                    .collect(Collectors.toList()));
        }

        return dto;
    }

    public static Configurations toEntity(ConfigurationsDTO dto ){
        if (dto == null) return null;

        Configurations config = new Configurations();
        config.setConfigurationId(dto.getConfigurationId());
        config.setProductId(dto.getProductId());
        config.setCapabilityId(dto.getCapabilityId());
        config.setTestCaseId(dto.getTestCaseId());
        config.setConfigName(dto.getConfigName());
        config.setNumberOfFields(dto.getNumberOfFields());

        // Map fields
        if (dto.getFields() != null) {
            config.setFields(dto.getFields().stream()
                    .map(FieldMapper::toEntity)
                    .collect(Collectors.toList()));
        }

        return config;
    }
}
