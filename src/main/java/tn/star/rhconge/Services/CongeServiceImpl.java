package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Conge;
import tn.star.rhconge.Repositories.CongeRepository;
import tn.star.rhconge.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class CongeServiceImpl implements CongeService {

    @Autowired
    private CongeRepository congeRepository;

    @Override
    public Conge saveConge(Conge conge) {
        return congeRepository.save(conge);
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

        if (congeDetails.getDemande() != null) {
            existingConge.setDemande(congeDetails.getDemande());
        }

        return congeRepository.save(existingConge);
    }

    @Override
    public void deleteConge(int id) {
        congeRepository.deleteById(id);
    }
}
