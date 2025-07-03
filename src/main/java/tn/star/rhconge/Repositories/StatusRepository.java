package tn.star.rhconge.Repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.star.rhconge.Entities.Status;

public interface StatusRepository extends JpaRepository<Status, Integer> {
}
