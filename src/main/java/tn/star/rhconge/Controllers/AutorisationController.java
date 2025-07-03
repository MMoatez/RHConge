package tn.star.rhconge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.Autorisation;
import tn.star.rhconge.Services.AutorisationService;

import java.util.List;

@RestController
@RequestMapping("/autorisation")
public class AutorisationController {

    @Autowired
    private AutorisationService autorisationService;

    @GetMapping
    public List<Autorisation> getAll() {
        return autorisationService.getAllAutorisations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Autorisation> getById(@PathVariable int id) {
        return ResponseEntity.ok(autorisationService.getAutorisationById(id));
    }

    @PostMapping
    public Autorisation create(@RequestBody Autorisation autorisation) {
        return autorisationService.saveAutorisation(autorisation);
    }

    @PutMapping("/{id}")
    public Autorisation update(@PathVariable int id, @RequestBody Autorisation autorisation) {
        return autorisationService.updateAutorisation(id, autorisation);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        autorisationService.deleteAutorisation(id);
    }


}
