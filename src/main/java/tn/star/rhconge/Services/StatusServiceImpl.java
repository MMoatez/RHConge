package tn.star.rhconge.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.Status;
import tn.star.rhconge.Repositories.StatusRepository;
import tn.star.rhconge.exception.ResourceNotFoundException;

import java.util.List;

@Service
public class StatusServiceImpl implements StatusService {

    @Autowired
    private StatusRepository statusRepository;

    @Override
    public Status saveStatus(Status status) {
        return statusRepository.save(status);
    }

    @Override
    public List<Status> saveAllStatuses(List<Status> statuses) {
        return statusRepository.saveAll(statuses);
    }

    @Override
    public List<Status> getAllStatuses() {
        return statusRepository.findAll();
    }

    @Override
    public Status getStatusById(int id) {
        return statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status not found with id " + id));
    }

    @Override
    public Status updateStatus(int id, Status statusDetails) {
        Status existingStatus = statusRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Status not found with id " + id));

        existingStatus.setNom(statusDetails.getNom());
        return statusRepository.save(existingStatus);
    }

    @Override
    public void deleteStatus(int id) {
        statusRepository.deleteById(id);
    }
}
