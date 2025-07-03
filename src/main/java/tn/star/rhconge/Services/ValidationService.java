package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.Validation;

import java.util.List;
import java.util.Optional;

public interface ValidationService {

    Validation createValidation(Validation validation);

    Validation updateValidation(Validation validation);

    void deleteValidation(int id);

    Optional<Validation> getValidationById(int id);

    List<Validation> getAllValidations();
}
