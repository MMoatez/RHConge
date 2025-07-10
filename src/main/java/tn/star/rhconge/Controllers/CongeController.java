package tn.star.rhconge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.Conge;
import tn.star.rhconge.Services.CongeService;

import java.util.List;

@RestController
@RequestMapping("/conges")
public class CongeController {

    @Autowired
    private CongeService congeService;

    @GetMapping
    public List<Conge> getAllConges() {
        return congeService.getAllConges();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Conge> getCongeById(@PathVariable int id) {
        Conge conge = congeService.getCongeById(id);
        return ResponseEntity.ok(conge);
    }



    @PostMapping("/{matricule}")
    public ResponseEntity<Conge> addConge(@RequestBody Conge conge, @PathVariable int matricule) {
        Conge saved = congeService.saveConge(conge, matricule);
        return ResponseEntity.ok(saved);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Conge> updateConge(@PathVariable int id, @RequestBody Conge conge) {
        Conge updatedConge = congeService.updateConge(id, conge);
        return ResponseEntity.ok(updatedConge);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteConge(@PathVariable int id) {
        congeService.deleteConge(id);
        return ResponseEntity.noContent().build();
    }
}
