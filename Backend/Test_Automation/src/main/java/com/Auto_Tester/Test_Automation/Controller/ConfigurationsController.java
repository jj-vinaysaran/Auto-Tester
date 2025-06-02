package com.Auto_Tester.Test_Automation.Controller;

import com.Auto_Tester.Test_Automation.Model.Configurations;
import com.Auto_Tester.Test_Automation.Model.Field;
import com.Auto_Tester.Test_Automation.DTO.ConfigurationsDTO;
import com.Auto_Tester.Test_Automation.DTO.FieldDTO;
import com.Auto_Tester.Test_Automation.Repository.ConfigurationsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/configurations")
public class ConfigurationsController {

    @Autowired
    private ConfigurationsRepository configurationsRepository;

    //  Create New Configuration
    @PostMapping
    public ResponseEntity<ConfigurationsDTO> createConfiguration(@RequestBody ConfigurationsDTO configurationsDTO) {
        Configurations config = new Configurations();
        config.setProductId(configurationsDTO.getProductId());
        config.setCapabilityId(configurationsDTO.getCapabilityId());
        config.setTestCaseId(configurationsDTO.getTestCaseId());
        config.setConfigName(configurationsDTO.getConfigName());

        List<Field> fields = configurationsDTO.getFields().stream().map(fieldDTO -> {
            Field field = new Field();
            field.setFieldName(fieldDTO.getFieldName());
            field.setValue(fieldDTO.getValue());
            return field;
        }).collect(Collectors.toList());

        config.setFields(fields);
        config.setNumberOfFields(fields.size());

        Configurations savedConfig = configurationsRepository.save(config);

        // Prepare Response DTO
        configurationsDTO.setConfigurationId(savedConfig.getConfigurationId());
        configurationsDTO.setNumberOfFields(savedConfig.getNumberOfFields());

        return ResponseEntity.ok(configurationsDTO);
    }

    //  Update Existing Configuration (Edit + Add Fields)
    @PutMapping("/{id}")
    public ResponseEntity<ConfigurationsDTO> updateConfiguration(@PathVariable String id, @RequestBody ConfigurationsDTO configurationsDTO) {
        Optional<Configurations> optionalConfig = configurationsRepository.findById(id);
        if (optionalConfig.isPresent()) {
            Configurations config = optionalConfig.get();

            // Update basic details
            if (configurationsDTO.getConfigName() != null) {
                config.setConfigName(configurationsDTO.getConfigName());
            }

            // Update or add fields
            if (configurationsDTO.getFields() != null) {
                List<Field> existingFields = config.getFields();
                List<FieldDTO> incomingFields = configurationsDTO.getFields();

                for (FieldDTO fieldDTO : incomingFields) {
                    boolean updated = false;
                    for (Field existingField : existingFields) {
                        if (existingField.getFieldName().equalsIgnoreCase(fieldDTO.getFieldName())) {
                            existingField.setValue(fieldDTO.getValue());
                            updated = true;
                            break;
                        }
                    }
                    if (!updated) {
                        // New field, add it
                        Field newField = new Field();
                        newField.setFieldName(fieldDTO.getFieldName());
                        newField.setValue(fieldDTO.getValue());
                        existingFields.add(newField);
                    }
                }
                config.setFields(existingFields);
                config.setNumberOfFields(existingFields.size());
            }

            Configurations updatedConfig = configurationsRepository.save(config);

            // Prepare Response DTO
            ConfigurationsDTO responseDTO = new ConfigurationsDTO();
            responseDTO.setConfigurationId(updatedConfig.getConfigurationId());
            responseDTO.setConfigName(updatedConfig.getConfigName());
            responseDTO.setProductId(updatedConfig.getProductId());
            responseDTO.setCapabilityId(updatedConfig.getCapabilityId());
            responseDTO.setTestCaseId(updatedConfig.getTestCaseId());
            responseDTO.setNumberOfFields(updatedConfig.getNumberOfFields());

            List<FieldDTO> fieldDTOs = updatedConfig.getFields().stream().map(f -> {
                FieldDTO fieldDTO = new FieldDTO();
                fieldDTO.setFieldName(f.getFieldName());
                fieldDTO.setValue(f.getValue());
                return fieldDTO;
            }).collect(Collectors.toList());
            responseDTO.setFields(fieldDTOs);

            return ResponseEntity.ok(responseDTO);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    //  Get All Configurations
    @GetMapping
    public ResponseEntity<List<ConfigurationsDTO>> getAllConfigurations() {
        List<Configurations> configs = configurationsRepository.findAll();
        List<ConfigurationsDTO> response = configs.stream().map(config -> {
            ConfigurationsDTO dto = new ConfigurationsDTO();
            dto.setConfigurationId(config.getConfigurationId());
            dto.setConfigName(config.getConfigName());
            dto.setProductId(config.getProductId());
            dto.setCapabilityId(config.getCapabilityId());
            dto.setTestCaseId(config.getTestCaseId());
            dto.setNumberOfFields(config.getNumberOfFields());

            List<FieldDTO> fieldDTOs = config.getFields().stream().map(f -> {
                FieldDTO fieldDTO = new FieldDTO();
                fieldDTO.setFieldName(f.getFieldName());
                fieldDTO.setValue(f.getValue());
                return fieldDTO;
            }).collect(Collectors.toList());
            dto.setFields(fieldDTOs);

            return dto;
        }).collect(Collectors.toList());

        return ResponseEntity.ok(response);
    }

    //  Get Configuration by ID
    @GetMapping("/{id}")
    public ResponseEntity<ConfigurationsDTO> getConfigurationById(@PathVariable String id) {
        Optional<Configurations> configOptional = configurationsRepository.findById(id);
        if (configOptional.isPresent()) {
            Configurations config = configOptional.get();
            ConfigurationsDTO dto = new ConfigurationsDTO();
            dto.setConfigurationId(config.getConfigurationId());
            dto.setConfigName(config.getConfigName());
            dto.setProductId(config.getProductId());
            dto.setCapabilityId(config.getCapabilityId());
            dto.setTestCaseId(config.getTestCaseId());
            dto.setNumberOfFields(config.getNumberOfFields());

            List<FieldDTO> fieldDTOs = config.getFields().stream().map(f -> {
                FieldDTO fieldDTO = new FieldDTO();
                fieldDTO.setFieldName(f.getFieldName());
                fieldDTO.setValue(f.getValue());
                return fieldDTO;
            }).collect(Collectors.toList());
            dto.setFields(fieldDTOs);

            return ResponseEntity.ok(dto);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete Configuration
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteConfiguration(@PathVariable String id) {
        if (configurationsRepository.existsById(id)) {
            configurationsRepository.deleteById(id);
            return ResponseEntity.ok("Configuration deleted successfully.");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
