package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.User;
import tn.star.rhconge.Entities.Validation;
import tn.star.rhconge.Repositories.ValidationRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ValidationServiceImpl implements ValidationService {

    private final ValidationRepository validationRepository;
    @Autowired
    private EmailService emailService;


    @Autowired
    private AsynchronizedTasks asynchronizedTasks;


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
    public Validation updateValidation(Validation updatedValidation) {
        Validation existing = validationRepository.findById(updatedValidation.getId())
                .orElseThrow(() -> new RuntimeException("Validation introuvable"));

        // ✅ Mettre à jour les champs
        existing.setApprouve(updatedValidation.getApprouve());
        existing.setDescription(updatedValidation.getDescription());
        existing.setDateV(LocalDateTime.now());

        Validation saved = validationRepository.save(existing);

        // ✅ Envoi d'un email au demandeur
        try {
            User demandeur = saved.getIdDemande().getMatriculeDemandeur();
            String decision = saved.getApprouve() != null && saved.getApprouve() ? "APPROUVÉE" : "REFUSÉE";

            asynchronizedTasks.sendEmail(
                    demandeur.getEmail(),
                    "Mise à jour de votre demande d'autorisation",
                    "Bonjour " + demandeur.getPrenom() + ",\n\n" +
                            "Votre demande d'autorisation a été " + decision + " par " +
                            saved.getMatriculeValidateur().getPrenom() + " " + saved.getMatriculeValidateur().getNom() + ".\n" +
                            "Remarque : " + (saved.getDescription() != null ? saved.getDescription() : "Aucune") + "\n\n" +
                            "Merci de consulter votre espace RH pour plus de détails.\n\nCordialement."
            );
        } catch (Exception e) {
            System.err.println("Erreur lors de l'envoi de l'email au demandeur : " + e.getMessage());
        }

        return saved;
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
