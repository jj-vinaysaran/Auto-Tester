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
    public ResponseEntity<UserInputDTO> createUserInput(@RequestBody UserInputDTO userInputDTO) {
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
