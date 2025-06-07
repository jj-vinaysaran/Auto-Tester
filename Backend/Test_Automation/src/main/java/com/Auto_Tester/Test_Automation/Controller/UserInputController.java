package com.Auto_Tester.Test_Automation.Controller;

import com.Auto_Tester.Test_Automation.DTO.UserInputDTO;
import com.Auto_Tester.Test_Automation.Mapper.UserInputMapper;
import com.Auto_Tester.Test_Automation.Model.UserInput;
import com.Auto_Tester.Test_Automation.Repository.UserInputRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/userinput")
public class UserInputController {

    @Autowired
    private UserInputRepository userInputRepository;

    @PostMapping
    public ResponseEntity<?> createUserInput(@RequestBody UserInputDTO userInputDTO) {
        String primaryPhone = userInputDTO.getPrimaryContactPhone();
        String secondaryPhone = userInputDTO.getSecondaryContactPhone();

        String phoneRegex = "^\\d{10}$";

        primaryPhone = primaryPhone != null ? primaryPhone.trim() : null;
        secondaryPhone = secondaryPhone != null ? secondaryPhone.trim() : null;

        if (primaryPhone == null || !primaryPhone.matches(phoneRegex)) {
            return ResponseEntity.badRequest().body("Primary contact phone number must be exactly 10 digits.");
        }

        if (secondaryPhone != null && !secondaryPhone.matches(phoneRegex)) {
            return ResponseEntity.badRequest().body("Secondary contact phone number must be exactly 10 digits.");
        }

        if (secondaryPhone != null && primaryPhone.equals(secondaryPhone)) {
            return ResponseEntity.badRequest().body("Primary and secondary phone numbers must be different.");
        }

        UserInput userInput = UserInputMapper.toEntity(userInputDTO);
        UserInput savedUserInput = userInputRepository.save(userInput);

        return ResponseEntity.ok(UserInputMapper.toDTO(savedUserInput));
    }


    @GetMapping("/{id}")
    public ResponseEntity<UserInputDTO> getUserInputById(@PathVariable String id) {
        return userInputRepository.findById(id)
                .map(UserInputMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserInputDTO>> getAllUserInputs() {
        List<UserInputDTO> userInputs = userInputRepository.findAll()
                .stream()
                .map(UserInputMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(userInputs);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserInputDTO> updateUserInput(@PathVariable String id, @RequestBody UserInputDTO userInputDTO) {
        return userInputRepository.findById(id)
                .map(existingUserInput -> {
                    UserInput updatedInput = UserInputMapper.toEntity(userInputDTO);
                    updatedInput.setUserInputId(existingUserInput.getUserInputId()); // Keep ID unchanged
                    UserInput savedInput = userInputRepository.save(updatedInput);
                    return ResponseEntity.ok(UserInputMapper.toDTO(savedInput));
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
