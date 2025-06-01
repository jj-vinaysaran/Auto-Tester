package com.Auto_Tester.Test_Automation.Mapper;

import com.Auto_Tester.Test_Automation.DTO.FieldDTO;
import com.Auto_Tester.Test_Automation.Model.Field;

public class FieldMapper {

    public static FieldDTO toDTO(Field field) {
        if (field == null) return null;

        FieldDTO dto = new FieldDTO();
        dto.setFieldName(field.getFieldName());
        dto.setValue(field.getValue());
        return dto;
    }

    public static Field toEntity(FieldDTO dto) {
        if (dto == null) return null;

        Field field = new Field();
        field.setFieldName(dto.getFieldName());
        field.setValue(dto.getValue());
        return field;
    }
}

