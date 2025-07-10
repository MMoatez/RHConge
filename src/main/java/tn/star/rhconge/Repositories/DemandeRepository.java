package tn.star.rhconge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.star.rhconge.Entities.Demande;

import java.util.List;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
    List<Demande> findByMatriculeDemandeur_Matricule(int matricule);

}
