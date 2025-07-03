package tn.star.rhconge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.Status;
import tn.star.rhconge.Services.StatusService;

import java.util.List;

@RestController
@RequestMapping("/status")
public class StatusController {

    @Autowired
    private StatusService statusService;

    // Récupérer tous les status
    @GetMapping
    public List<Status> getAll() {
        return statusService.getAllStatuses();
    }

    // Récupérer un status par id
    @GetMapping("/{id}")
    public ResponseEntity<Status> getById(@PathVariable int id) {
        return ResponseEntity.ok(statusService.getStatusById(id));
    }

    // Ajouter un seul status
    @PostMapping("/ajout")
    public Status create(@RequestBody Status status) {
        return statusService.saveStatus(status);
    }

    // Ajouter plusieurs status (batch)
    @PostMapping("/ajoutP")
    public List<Status> createAll(@RequestBody List<Status> statuses) {
        return statusService.saveAllStatuses(statuses);
    }

    // Mettre à jour un status
    @PutMapping("/{id}")
    public Status update(@PathVariable int id, @RequestBody Status status) {
        return statusService.updateStatus(id, status);
    }

    // Supprimer un status
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        statusService.deleteStatus(id);
    }
}
