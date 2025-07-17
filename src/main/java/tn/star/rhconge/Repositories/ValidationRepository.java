package tn.star.rhconge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.star.rhconge.Entities.Validation;

import java.util.List;

public interface ValidationRepository extends JpaRepository<Validation, Integer> {


    List<Validation> findByMatriculeValidateur_Matricule(int matricule);

    List<Validation> findByIdDemande_Id(int id);
}
