package com.Auto_Tester.Test_Automation.DTO;

import lombok.Data;

@Data
public class UserInputDTO {

    private String userInputId;
    // Primary Contact Details
    private String primaryContactSalutation;
    private String primaryContactFirstName;
    private String primaryContactLastName;
    private String primaryContactGender;
    private String primaryContactTitle;
    private String primaryContactEmail;
    private String primaryContactPhone;

    // Secondary Contact Details
    private String secondaryContactSalutation;
    private String secondaryContactFirstName;
    private String secondaryContactLastName;
    private String secondaryContactGender;
    private String secondaryContactTitle;
    private String secondaryContactEmail;
    private String secondaryContactPhone;

}
