package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.Status;
import java.util.List;

public interface StatusService {
    Status saveStatus(Status status);
    List<Status> saveAllStatuses(List<Status> statuses);
    List<Status> getAllStatuses();
    Status getStatusById(int id);
    Status updateStatus(int id, Status status);
    void deleteStatus(int id);
}
