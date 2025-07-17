package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.*;
import tn.star.rhconge.Repositories.AutorisationRepository;
import tn.star.rhconge.Repositories.DemandeRepository;
import tn.star.rhconge.Repositories.StatusRepository;
import tn.star.rhconge.Repositories.UserRepository;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class AutorisationServiceImpl implements AutorisationService {

    @Autowired
    private AutorisationRepository autorisationRepository;

    @Autowired
    private DemandeRepository demandeRepository;

    @Autowired
    private StatusRepository statusRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ValidationService validationService;
    @Autowired
    private EmailService emailService;



    @Override
    public Autorisation saveAutorisation(Autorisation autorisation, int matricule) {
        // 1. Récupérer l'utilisateur (demandeur)
        User demandeur = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 2. Vérifier que la durée ne dépasse pas hAutorisation sauf si motif = "Service"
        if (autorisation.getDuree() > demandeur.getHAutorisation()
                && !"Service".equalsIgnoreCase(autorisation.getMotif())) {
            throw new RuntimeException("La durée dépasse le nombre d'heures d'autorisation restantes et le motif n'est pas 'Service'.");
        }

        // 3. Sauvegarder l'autorisation
        Autorisation savedAutorisation = autorisationRepository.save(autorisation);

        // 4. Récupérer le statut "En attente"
        Status status = statusRepository.findByNom("EN_ATTENTE")
                .orElseThrow(() -> new RuntimeException("Statut 'EN_ATTENTE' non trouvé"));

        // 5. Créer la demande
        Demande demande = new Demande();
        demande.setAutorisation(savedAutorisation);
        demande.setStatus(status);
        demande.setDateModif(LocalDateTime.now());
        demande.setMatriculeDemandeur(demandeur);

        // 6. Sauvegarder la demande (temporairement, sans nbValidateur)
        demandeRepository.save(demande);

        // 7. Chercher les managers et créer validations
        int nbValidateurs = 0;
        User manager1 = demandeur.getManager();

        if (manager1 != null) {
            Validation v1 = new Validation();
            v1.setIdDemande(demande);
            v1.setMatriculeValidateur(manager1);
            v1.setApprouve(null);
            validationService.createValidation(v1);
            nbValidateurs++;
            // Envoi d'email au manager1
            emailService.sendEmail(
                    manager1.getEmail(),
                    "Nouvelle demande d'autorisation",
                    "Bonjour " + manager1.getPrenom() + ",\n\n" +
                            "Une nouvelle demande d'autorisation a été soumise par " +
                            demandeur.getPrenom() + " " + demandeur.getNom() + ".\n" +
                            "Merci de la consulter et de la valider sur le portail RH."
            );





            User manager2 = manager1.getManager();
            if (manager2 != null) {
                Validation v2 = new Validation();
                v2.setIdDemande(demande);
                v2.setMatriculeValidateur(manager2);
                v2.setApprouve(null);
                validationService.createValidation(v2);
                nbValidateurs++;
                // Envoi d'email au manager2
                emailService.sendEmail(
                        manager2.getEmail(),
                        "Nouvelle demande d'autorisation",
                        "Bonjour " + manager2.getPrenom() + ",\n\n" +
                                "Une nouvelle demande d'autorisation a été soumise par " +
                                demandeur.getPrenom() + " " + demandeur.getNom() + ".\n" +
                                "Merci de la consulter et de la valider sur le portail RH_Congé."
                );
            }

            demande.setNbValidateur(nbValidateurs);
        } else {
            Status accepte = statusRepository.findByNom("APPROUVE")
                    .orElseThrow(() -> new RuntimeException("Status 'APPROUVE' non trouvé"));
            // ✅ Envoi d'email au demandeur connecté
            emailService.sendEmail(
                    demandeur.getEmail(),
                    "Demande d'autorisation acceptée automatiquement",
                    "Bonjour " + demandeur.getPrenom() + ",\n\n" +
                            "Votre demande d'autorisation a été acceptée automatiquement car vous n'avez pas de manager référencé dans le système.\n\n" +
                            "Cordialement,\nService RH_Congé"
            );
            demande.setStatus(accepte);
            demande.setNbValidateur(0);
        }

        // 8. Mettre à jour la demande
        demandeRepository.save(demande);

        return savedAutorisation;
    }



    @Override
    public void deleteAutorisation(int id) {
        autorisationRepository.deleteById(id);
    }

    @Override
    public Autorisation updateAutorisation(int id, Autorisation autorisation) {
        Autorisation existing = autorisationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autorisation non trouvée avec id = " + id));

        existing.setDateSortie(autorisation.getDateSortie());
        existing.setMotif(autorisation.getMotif());
        existing.setDuree(autorisation.getDuree());
        existing.setDescription(autorisation.getDescription());

        return autorisationRepository.save(existing);
    }

    @Override
    public List<Autorisation> getAllAutorisations() {
        return autorisationRepository.findAll();
    }

    @Override
    public Autorisation getAutorisationById(int id) {
        return autorisationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Autorisation non trouvée avec id = " + id));
    }
}
