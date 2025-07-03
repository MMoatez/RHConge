package tn.star.rhconge.Controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.Validation;
import tn.star.rhconge.Services.ValidationService;

import java.util.List;

@RestController
@RequestMapping("/api/validations")

public class ValidationController {

    private final ValidationService validationService;

    public ValidationController(ValidationService validationService) {
        this.validationService = validationService;
    }

    @PostMapping
    public ResponseEntity<Validation> createValidation(@RequestBody Validation validation) {
        Validation created = validationService.createValidation(validation);
        return ResponseEntity.ok(created);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Validation> getValidationById(@PathVariable int id) {
        return validationService.getValidationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Validation>> getAllValidations() {
        return ResponseEntity.ok(validationService.getAllValidations());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Validation> updateValidation(@PathVariable int id, @RequestBody Validation validation) {
        if (id != validation.getId()) {
            return ResponseEntity.badRequest().build();
        }
        try {
            Validation updated = validationService.updateValidation(validation);
            return ResponseEntity.ok(updated);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValidation(@PathVariable int id) {
        validationService.deleteValidation(id);
        return ResponseEntity.noContent().build();
    }
}
