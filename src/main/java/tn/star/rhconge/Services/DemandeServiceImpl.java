package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Demande;
import tn.star.rhconge.Entities.Status;
import tn.star.rhconge.Repositories.DemandeRepository;
import tn.star.rhconge.Repositories.StatusRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DemandeServiceImpl implements DemandeService {

    private final DemandeRepository demandeRepository;
    private final StatusRepository statusRepository;

    @Autowired
    public DemandeServiceImpl(DemandeRepository demandeRepository, StatusRepository statusRepository) {
        this.demandeRepository = demandeRepository;
        this.statusRepository = statusRepository;
    }

    @Override
    public Demande createDemande(Demande demande) {
        // Affecter la date système
        demande.setDateModif(LocalDateTime.now());

        // Affecter le status par défaut (id = 1)
        Optional<Status> defaultStatus = statusRepository.findById(1);
        if (defaultStatus.isPresent()) {
            demande.setStatus(defaultStatus.get());
        } else {
            throw new RuntimeException("Status avec ID 1 non trouvé. Veuillez vérifier la table Status.");
        }

        return demandeRepository.save(demande);
    }

    @Override
    public List<Demande> getAllDemandes() {
        return demandeRepository.findAll();
    }

    @Override
    public Optional<Demande> getDemandeById(int id) {
        return demandeRepository.findById(id);
    }

    @Override
    public Demande updateDemande(int id, Demande demande) {
        if (!demandeRepository.existsById(id)) {
            throw new RuntimeException("Demande non trouvée");
        }

        // Mettre à jour la date de modification automatiquement
        demande.setDateModif(LocalDateTime.now());

        return demandeRepository.save(demande);
    }

    @Override
    public void deleteDemande(int id) {
        demandeRepository.deleteById(id);
    }
    @Override
    public List<Demande> getDemandesByMatricule(int matricule) {
        return demandeRepository.findByMatriculeDemandeur_Matricule(matricule);
    }



}
