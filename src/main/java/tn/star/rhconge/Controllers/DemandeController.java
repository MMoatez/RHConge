package tn.star.rhconge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.Demande;
import tn.star.rhconge.Services.DemandeService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/demandes")
public class DemandeController {

    @Autowired
    private DemandeService demandeService;

    @GetMapping
    public List<Demande> getAll() {
        return demandeService.getAllDemandes();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Demande> getById(@PathVariable int id) {
        Optional<Demande> demande = demandeService.getDemandeById(id);
        return demande.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public Demande create(@RequestBody Demande demande) {
        return demandeService.createDemande(demande);
    }

    @PutMapping("/{id}")
    public Demande update(@PathVariable int id, @RequestBody Demande demande) {
        return demandeService.updateDemande(id, demande);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        demandeService.deleteDemande(id);
    }

    @GetMapping("/user/{matricule}")
    public ResponseEntity<List<Demande>> getDemandesByUser(@PathVariable int matricule) {
        List<Demande> demandes = demandeService.getDemandesByMatricule(matricule);
        return ResponseEntity.ok(demandes);
    }
}
