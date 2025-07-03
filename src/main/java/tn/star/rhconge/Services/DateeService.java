package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.Datee;
import java.util.List;

public interface DateeService {

    Datee saveDatee(Datee datee);

    List<Datee> getAllDatees();

    Datee getDateeById(int id);

    Datee updateDatee(int id, Datee datee);

    void deleteDatee(int id);
}
