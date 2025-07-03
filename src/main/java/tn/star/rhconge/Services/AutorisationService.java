package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.Autorisation;
import java.util.List;

public interface AutorisationService {
    Autorisation saveAutorisation(Autorisation autorisation);
    void deleteAutorisation(int id);
    Autorisation updateAutorisation(int id, Autorisation autorisation);
    List<Autorisation> getAllAutorisations();
    Autorisation getAutorisationById(int id);
}
