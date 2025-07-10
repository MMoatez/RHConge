package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.Conge;

import java.util.List;

public interface CongeService {

    Conge saveConge(Conge conge, int matricule);
    List<Conge> getAllConges();
    Conge getCongeById(int id);
    Conge updateConge(int id, Conge conge);
    void deleteConge(int id);
}
