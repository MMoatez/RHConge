package tn.star.rhconge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.star.rhconge.Entities.Demande;

public interface DemandeRepository extends JpaRepository<Demande, Integer> {
}
