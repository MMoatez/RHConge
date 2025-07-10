package tn.star.rhconge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.star.rhconge.Entities.Status;

import java.util.Optional;

public interface StatusRepository extends JpaRepository<Status, Integer> {
    Optional<Status> findByNom // ou selon ton champ réel
    (String nom);
}
