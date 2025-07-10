package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.Demande;
import java.util.List;
import java.util.Optional;

public interface DemandeService {
    Demande createDemande(Demande demande);
    List<Demande> getAllDemandes();
    Optional<Demande> getDemandeById(int id);
    Demande updateDemande(int id, Demande demande);
    void deleteDemande(int id);
    List<Demande> getDemandesByMatricule(int matricule);

}
