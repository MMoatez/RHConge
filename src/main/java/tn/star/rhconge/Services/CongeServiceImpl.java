package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Conge;
import tn.star.rhconge.Entities.Demande;
import tn.star.rhconge.Entities.Status;
import tn.star.rhconge.Entities.User;
import tn.star.rhconge.Repositories.CongeRepository;
import tn.star.rhconge.Repositories.DemandeRepository;
import tn.star.rhconge.Repositories.StatusRepository;
import tn.star.rhconge.Repositories.UserRepository;
import tn.star.rhconge.exception.ResourceNotFoundException;
import tn.star.rhconge.Entities.Validation;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CongeServiceImpl implements CongeService {


    @Autowired
    private CongeRepository congeRepository;

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
    
    
    @Autowired
    private AsynchronizedTasks asynchronizedTasks;

    @Override
    public Conge saveConge(Conge conge, int matricule) {
        // 1. Récupérer l'utilisateur par matricule
        User demandeur = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 2. Calculer le total des congés restants
        int totalRestants = demandeur.getCongesRestants();

        // 3. Vérifier que le nombre de jours demandé ne dépasse pas les congés restants
        if (conge.getNbJour() > totalRestants) {
            throw new RuntimeException("Ta demande dépasse le nombre de jours de congé restants (" + totalRestants + " jours).");
        }

        // 4. Vérifier s'il a encore des jours disponibles
        if (totalRestants <= 0) {
            throw new RuntimeException("Vous ne pouvez pas faire de demande de congé. Congés restants = 0.");
        }

        // 5. Enregistrer le congé
        Conge savedConge = congeRepository.save(conge);

        // 6. Récupérer le statut "En attente"
        Status statusAttente = statusRepository.findByNom("EN_ATTENTE")
                .orElseThrow(() -> new RuntimeException("Status 'EN_ATTENTE' non trouvé"));

        // 7. Créer la demande
        Demande demande = new Demande();
        demande.setConge(savedConge);
        demande.setStatus(statusAttente);
        demande.setNbValidateur(2);
        demande.setDateModif(LocalDateTime.now());
        demande.setMatriculeDemandeur(demandeur);

        // 8. Sauvegarder la demande
        demandeRepository.save(demande);

        // 9. Chercher les managers
        int nbValidateurs = 0;
        User manager1 = demandeur.getManager();

        if (manager1 != null) {
            // 1er niveau
            Validation v1 = new Validation();
            v1.setIdDemande(demande);
            v1.setMatriculeValidateur(manager1);
            v1.setApprouve(null);
            validationService.createValidation(v1);
            nbValidateurs++;
            // Envoi d'email au manager1
            asynchronizedTasks.sendEmail(
                    manager1.getEmail(),
                    "Nouvelle demande d'autorisation",
                    "Bonjour " + manager1.getPrenom() + ",\n\n" +
                            "Une nouvelle demande d'autorisation a été soumise par " +
                            demandeur.getPrenom() + " " + demandeur.getNom() + ".\n" +
                            "Merci de la consulter et de la valider sur le portail RH."
            );

            User manager2 = manager1.getManager();
            if (manager2 != null) {
                // 2e niveau
                Validation v2 = new Validation();
                v2.setIdDemande(demande);
                v2.setMatriculeValidateur(manager2);
                v2.setApprouve(null);
                validationService.createValidation(v2);
                nbValidateurs++;
                // Envoi d'email au manager2
                asynchronizedTasks.sendEmail(
                        manager2.getEmail(),
                        "Nouvelle demande d'autorisation",
                        "Bonjour " + manager2.getPrenom() + ",\n\n" +
                                "Une nouvelle demande d'autorisation a été soumise par " +
                                demandeur.getPrenom() + " " + demandeur.getNom() + ".\n" +
                                "Merci de la consulter et de la valider sur le portail RH."
                );
            }

            demande.setNbValidateur(nbValidateurs);

        } else {
            // Aucun manager : accepter directement
            Status approuve = statusRepository.findByNom("APPROUVE")
                    .orElseThrow(() -> new RuntimeException("Statut 'APPROUVE' non trouvé"));
            demande.setStatus(approuve);
            demande.setNbValidateur(0);

            // ✅ Mise à jour du solde de congés uniquement si la demande concerne un congé
            if (demande.getConge() != null) {
                User user = demande.getMatriculeDemandeur();
                int nbJoursDemandes = demande.getConge().getNbJour();


                if (user.getCongesAnnuelsRestants() >= nbJoursDemandes) {
                    user.setCongesAnnuelsRestants(user.getCongesAnnuelsRestants() - nbJoursDemandes);
                } else {
                    int reste = nbJoursDemandes - user.getCongesAnnuelsRestants();
                    user.setCongesAnnuelsRestants(0);
                    user.setCongesRestants(user.getCongesRestants() - reste);
                }

                user.setCongesPris(user.getCongesPris() + nbJoursDemandes);
                userRepository.save(user);
            }
        }

        // 10. Mettre à jour la demande avec les infos finales
        demandeRepository.save(demande);

        return savedConge;
    }



    @Override
    public List<Conge> getAllConges() {
        return congeRepository.findAll();
    }

    @Override
    public Conge getCongeById(int id) {
        return congeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conge not found with id " + id));
    }

    @Override
    public Conge updateConge(int id, Conge congeDetails) {
        Conge existingConge = congeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Conge not found with id " + id));

        existingConge.setNbJour(congeDetails.getNbJour());
        existingConge.setDateDebut(congeDetails.getDateDebut());
        existingConge.setDateFin(congeDetails.getDateFin());
        existingConge.setType(congeDetails.getType());



        return congeRepository.save(existingConge);
    }

    @Override
    public void deleteConge(int id) {
        congeRepository.deleteById(id);
    }
}
