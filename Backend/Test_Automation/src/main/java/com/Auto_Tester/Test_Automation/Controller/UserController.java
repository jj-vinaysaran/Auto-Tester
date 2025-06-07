package com.Auto_Tester.Test_Automation.Controller;

import com.Auto_Tester.Test_Automation.DTO.UserDTO;
import com.Auto_Tester.Test_Automation.Mapper.UserMapper;
import com.Auto_Tester.Test_Automation.Model.User;
import com.Auto_Tester.Test_Automation.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/signup")
    public ResponseEntity<?> createUser(@RequestBody UserDTO userDTO) {
        try {
            String username = userDTO.getUserName();
            String password = userDTO.getPassword();
            if (username == null || username.length() != 6) {
                return ResponseEntity.badRequest().body("Username must be exactly 6 characters.");
            }
            if (password == null || password.length() <= 8 || !password.matches("^(?=.*[a-zA-Z])(?=.*\\d).+$")) {
                return ResponseEntity.badRequest().body("Password must be longer than 8 characters and should be alphanumeric");
            }

            // Check if user already exists
            User existingUser = userRepository.findByUserName(username);
            System.out.println(" Existing User : " + existingUser);
            if (existingUser != null) {
                return ResponseEntity.status(409).body("Username already exists. Please choose a different username.");
            }

            // Save new user
            User user = UserMapper.toEntity(userDTO);
            User savedUser = userRepository.save(user);

            return ResponseEntity.ok(UserMapper.toDTO(savedUser));

        } catch (Exception e) {
            e.printStackTrace(); // For debugging
            return ResponseEntity.status(500).body("Internal Server Error: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return userRepository.findById(id)
                .map(UserMapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers() {
        List<UserDTO> users = userRepository.findAll()
                .stream()
                .map(UserMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(users);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody UserDTO userDTO) {
        return userRepository.findById(id)
                .map(existingUser -> {
                    existingUser.setUserName(userDTO.getUserName());
                    existingUser.setPassword(userDTO.getPassword());
                    User updatedUser = userRepository.save(existingUser);
                    return ResponseEntity.ok(UserMapper.toDTO(updatedUser));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        if (!userRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        userRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

