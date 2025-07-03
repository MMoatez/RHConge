package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Validation;
import tn.star.rhconge.Repositories.ValidationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final ValidationRepository validationRepository;

    @Autowired
    public ValidationServiceImpl(ValidationRepository validationRepository) {
        this.validationRepository = validationRepository;
    }

    @Override
    public Validation createValidation(Validation validation) {
        validation.setDateV(LocalDateTime.now()); // ⏰ fixer la date système
        return validationRepository.save(validation);
    }

    @Override
    public Validation updateValidation(Validation validation) {
        if (!validationRepository.existsById(validation.getId())) {
            throw new RuntimeException("Validation introuvable");
        }

        // Mettre à jour la date de modification automatiquement
        validation.setDateV(LocalDateTime.now());
        return validationRepository.save(validation);
    }

    @Override
    public void deleteValidation(int id) {
        validationRepository.deleteById(id);
    }

    @Override
    public Optional<Validation> getValidationById(int id) {
        return validationRepository.findById(id);
    }

    @Override
    public List<Validation> getAllValidations() {
        return validationRepository.findAll();
    }
}
