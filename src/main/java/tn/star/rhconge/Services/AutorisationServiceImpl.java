package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Autorisation;
import tn.star.rhconge.Repositories.AutorisationRepository;

import java.util.List;

@Service
public class AutorisationServiceImpl implements AutorisationService {

    @Autowired
    private AutorisationRepository autorisationRepository;

    @Override
    public Autorisation saveAutorisation(Autorisation autorisation) {
        return autorisationRepository.save(autorisation);
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
