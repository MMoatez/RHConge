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

    @Override
    public Conge saveConge(Conge conge, int matricule) {
        // 1. Enregistrer le congé
        Conge savedConge = congeRepository.save(conge);

        // 2. Récupérer le statut "En attente"
        Status statusAttente = statusRepository.findByNom("EN_ATTENTE")
                .orElseThrow(() -> new RuntimeException("Status 'EN_ATTENTE' non trouvé"));

        // 3. Récupérer l'utilisateur par matricule
        User demandeur = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 4. Créer la demande
        Demande demande = new Demande();
        demande.setConge(savedConge);
        demande.setStatus(statusAttente);
        demande.setNbValidateur(2);
        demande.setDateModif(LocalDateTime.now());
        demande.setMatriculeDemandeur(demandeur);

        // 5. Sauvegarder la demande
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
