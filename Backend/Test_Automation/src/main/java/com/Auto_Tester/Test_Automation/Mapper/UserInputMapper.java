package com.Auto_Tester.Test_Automation.Mapper;

import com.Auto_Tester.Test_Automation.DTO.UserInputDTO;
import com.Auto_Tester.Test_Automation.Model.UserInput;

public class UserInputMapper {

    public static UserInputDTO toDTO(UserInput userInput) {
        if (userInput == null) return null;

        UserInputDTO dto = new UserInputDTO();
        dto.setUserInputId(userInput.getUserInputId());
        dto.setPrimaryContactSalutation(userInput.getPrimaryContactSalutation());
        dto.setPrimaryContactFirstName(userInput.getPrimaryContactFirstName());
        dto.setPrimaryContactLastName(userInput.getPrimaryContactLastName());
        dto.setPrimaryContactGender(userInput.getPrimaryContactGender());
        dto.setPrimaryContactTitle(userInput.getPrimaryContactTitle());
        dto.setPrimaryContactEmail(userInput.getPrimaryContactEmail());
        dto.setPrimaryContactPhone(userInput.getPrimaryContactPhone());

        dto.setSecondaryContactSalutation(userInput.getSecondaryContactSalutation());
        dto.setSecondaryContactFirstName(userInput.getSecondaryContactFirstName());
        dto.setSecondaryContactLastName(userInput.getSecondaryContactLastName());
        dto.setSecondaryContactGender(userInput.getSecondaryContactGender());
        dto.setSecondaryContactTitle(userInput.getSecondaryContactTitle());
        dto.setSecondaryContactEmail(userInput.getSecondaryContactEmail());
        dto.setSecondaryContactPhone(userInput.getSecondaryContactPhone());

        return dto;
    }

    public static UserInput toEntity(UserInputDTO dto) {
        if (dto == null) return null;

        UserInput userInput = new UserInput();
        userInput.setUserInputId(dto.getUserInputId());
        userInput.setPrimaryContactSalutation(dto.getPrimaryContactSalutation());
        userInput.setPrimaryContactFirstName(dto.getPrimaryContactFirstName());
        userInput.setPrimaryContactLastName(dto.getPrimaryContactLastName());
        userInput.setPrimaryContactGender(dto.getPrimaryContactGender());
        userInput.setPrimaryContactTitle(dto.getPrimaryContactTitle());
        userInput.setPrimaryContactEmail(dto.getPrimaryContactEmail());
        userInput.setPrimaryContactPhone(dto.getPrimaryContactPhone());

        userInput.setSecondaryContactSalutation(dto.getSecondaryContactSalutation());
        userInput.setSecondaryContactFirstName(dto.getSecondaryContactFirstName());
        userInput.setSecondaryContactLastName(dto.getSecondaryContactLastName());
        userInput.setSecondaryContactGender(dto.getSecondaryContactGender());
        userInput.setSecondaryContactTitle(dto.getSecondaryContactTitle());
        userInput.setSecondaryContactEmail(dto.getSecondaryContactEmail());
        userInput.setSecondaryContactPhone(dto.getSecondaryContactPhone());

        return userInput;
    }
}
