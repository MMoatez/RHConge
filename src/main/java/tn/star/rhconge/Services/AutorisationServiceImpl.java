package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Autorisation;
import tn.star.rhconge.Entities.Demande;
import tn.star.rhconge.Entities.Status;
import tn.star.rhconge.Entities.User;
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
    @Override
    public Autorisation saveAutorisation(Autorisation autorisation, int matricule) {
        // 1. Sauvegarder l'autorisation
        Autorisation savedAutorisation = autorisationRepository.save(autorisation);

        // 2. Récupérer le statut "En attente"
        Status status = statusRepository.findByNom("EN_ATTENTE")
                .orElseThrow(() -> new RuntimeException("Statut 'En attente' non trouvé"));

        // 3. Récupérer l'utilisateur (demandeur)
        User demandeur = userRepository.findByMatricule(matricule)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // 4. Créer la demande
        Demande demande = new Demande();
        demande.setAutorisation(savedAutorisation);
        demande.setStatus(status);
        demande.setNbValidateur(2);
        demande.setDateModif(LocalDateTime.now());
        demande.setMatriculeDemandeur(demandeur);

        // 5. Sauvegarder la demande
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
