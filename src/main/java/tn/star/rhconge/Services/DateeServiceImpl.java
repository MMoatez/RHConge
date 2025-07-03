package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Datee;
import tn.star.rhconge.Repositories.DateeRepository;
import tn.star.rhconge.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class DateeServiceImpl implements DateeService {

    private final DateeRepository dateeRepository;

    @Autowired
    public DateeServiceImpl(DateeRepository dateeRepository) {
        this.dateeRepository = dateeRepository;
    }

    @Override
    public Datee saveDatee(Datee datee) {
        return dateeRepository.save(datee);
    }

    @Override
    public List<Datee> getAllDatees() {
        return dateeRepository.findAll();
    }

    @Override
    public Datee getDateeById(int id) {
        return dateeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Datee not found with id: " + id));
    }

    @Override
    public Datee updateDatee(int id, Datee updatedDatee) {
        Datee datee = getDateeById(id);
        datee.setDate(updatedDatee.getDate());
        datee.setMatin(updatedDatee.isMatin());
        datee.setApresMidi(updatedDatee.isApresMidi());
        datee.setConge(updatedDatee.getConge());

        return dateeRepository.save(datee);
    }

    @Override
    public void deleteDatee(int id) {
        dateeRepository.deleteById(id);
    }
}
