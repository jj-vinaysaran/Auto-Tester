package com.Auto_Tester.Test_Automation.Controller;

import com.Auto_Tester.Test_Automation.Model.Credentials;
import com.Auto_Tester.Test_Automation.Repository.CredentialRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/credentials")
public class CredentialsController {

    @Autowired
    private CredentialRepository credentialsRepository;

    @PostMapping
    public ResponseEntity<Credentials> createCredentials(@RequestBody Credentials credentials) {
        Credentials savedCredentials = credentialsRepository.save(credentials);
        return ResponseEntity.ok(savedCredentials);
    }

    @GetMapping
    public ResponseEntity<List<Credentials>> getAllCredentials() {
        List<Credentials> credentialsList = credentialsRepository.findAll();
        return ResponseEntity.ok(credentialsList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Credentials> getCredentialsById(@PathVariable String id) {
        Optional<Credentials> credentialsOptional = credentialsRepository.findById(id);
        return credentialsOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Credentials> updateCredentials(@PathVariable String id, @RequestBody Credentials updatedCredentials) {
        Optional<Credentials> credentialsOptional = credentialsRepository.findById(id);
        if (credentialsOptional.isPresent()) {
            Credentials credentials = credentialsOptional.get();
            credentials.setSalesforceLoginURL(updatedCredentials.getSalesforceLoginURL());
            credentials.setSalesforceLoginPassword(updatedCredentials.getSalesforceLoginPassword());
            credentials.setEnvironmentName(updatedCredentials.getEnvironmentName());
            credentials.setCustomerSignID(updatedCredentials.getCustomerSignID());
            credentials.setCustomerSignPassword(updatedCredentials.getCustomerSignPassword());
            credentials.setCounterSignUserID(updatedCredentials.getCounterSignUserID());
            credentials.setCounterSignPassword(updatedCredentials.getCounterSignPassword());

            Credentials savedCredentials = credentialsRepository.save(credentials);
            return ResponseEntity.ok(savedCredentials);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteCredentials(@PathVariable String id) {
        if (credentialsRepository.existsById(id)) {
            credentialsRepository.deleteById(id);
            return ResponseEntity.ok("Credentials deleted successfully!");
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
