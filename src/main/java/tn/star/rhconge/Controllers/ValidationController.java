package tn.star.rhconge.Controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.*;
import tn.star.rhconge.Repositories.*;
import tn.star.rhconge.Services.ValidationService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/validations")
public class ValidationController {

    private final ValidationService validationService;
    private final ValidationRepository validationRepository;
    private final DemandeRepository demandeRepository;
    private final StatusRepository statusRepository;
    private final UserRepository userRepository;

    public ValidationController(
            ValidationService validationService,
            ValidationRepository validationRepository,
            DemandeRepository demandeRepository,
            StatusRepository statusRepository,
            UserRepository userRepository
    ) {
        this.validationService = validationService;
        this.validationRepository = validationRepository;
        this.demandeRepository = demandeRepository;
        this.statusRepository = statusRepository;
        this.userRepository = userRepository;
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

    @GetMapping("/validateur/{matricule}")
    public ResponseEntity<List<Validation>> getValidationsByValidateur(@PathVariable int matricule) {
        List<Validation> list = validationRepository.findByMatriculeValidateur_Matricule(matricule);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Validation> updateValidation(@PathVariable int id, @RequestBody Validation validation) {
        if (id != validation.getId()) {
            return ResponseEntity.badRequest().build();
        }

        try {
            Validation updated = validationService.updateValidation(validation);

            Demande demande = updated.getIdDemande();
            List<Validation> validations = validationRepository.findByIdDemande_Id(demande.getId());

            long total = validations.size();
            long accepte = validations.stream().filter(v -> Boolean.TRUE.equals(v.getApprouve())).count();
            long refuse = validations.stream().filter(v -> Boolean.FALSE.equals(v.getApprouve())).count();

            if (refuse >= 1) {
                Status statutRefuse = statusRepository.findByNom("REJETE")
                        .orElseThrow(() -> new RuntimeException("Statut REJETE non trouvé"));
                demande.setStatus(statutRefuse);

            } else if (accepte == total && total > 0) {
                Status statutApprouve = statusRepository.findByNom("APPROUVE")
                        .orElseThrow(() -> new RuntimeException("Statut APPROUVE non trouvé"));
                demande.setStatus(statutApprouve);

                User user = demande.getMatriculeDemandeur();

                // ✅ Si demande de congé
                if (demande.getConge() != null) {
                    int nbJoursDemandes = demande.getConge().getNbJour();
                    int totalCongesDisponibles = user.getCongesRestants();
                    String typeConge = demande.getConge().getType().getName().toLowerCase();

                    if (totalCongesDisponibles < nbJoursDemandes) {
                        throw new RuntimeException("Pas assez de congés restants pour cette demande");
                    }

                    int bonus = switch (typeConge) {
                        case "mariage agent" -> 1;
                        case "circoncision" -> 2;
                        case "mariage enfant" -> 3;
                        case "deces parents" -> 4;
                        case "deces conjoint enfant" -> 6;
                        case "deces autre" -> 7;
                        default -> 0; // Congé de repos ou inconnu
                    };

                    int nouveauSolde = totalCongesDisponibles - nbJoursDemandes + bonus;
                    user.setCongesRestants(Math.max(0, nouveauSolde));
                    user.setCongesPris(user.getCongesPris() + nbJoursDemandes - bonus);
                    userRepository.save(user);
                }


                // ✅ Si demande d’autorisation
                else if (demande.getAutorisation() != null) {
                    Autorisation autorisation = demande.getAutorisation();

                    String motif = autorisation.getMotif(); // "Service" ou "Personnel"
                    int duree = autorisation.getDuree();   // en minutes

                    if (!"Service".equalsIgnoreCase(motif)) {
                        int hAutorisationRestantes = user.getHAutorisation();
                        if (hAutorisationRestantes < duree) {
                            throw new RuntimeException("Pas assez d'heures d'autorisation restantes.");
                        }
                        user.setHAutorisation(hAutorisationRestantes - duree);
                        userRepository.save(user);
                    }
                }
            } else {
                Status statutAttente = statusRepository.findByNom("EN_ATTENTE")
                        .orElseThrow(() -> new RuntimeException("Statut EN_ATTENTE non trouvé"));
                demande.setStatus(statutAttente);
            }

            demande.setDateModif(LocalDateTime.now());
            demandeRepository.save(demande);

            return ResponseEntity.ok(updated);

        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteValidation(@PathVariable int id) {
        validationService.deleteValidation(id);
        return ResponseEntity.noContent().build();
    }
}
