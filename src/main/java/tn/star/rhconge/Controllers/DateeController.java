package tn.star.rhconge.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.Datee;
import tn.star.rhconge.Services.DateeService;
import tn.star.rhconge.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/datee")
public class DateeController {

    private final DateeService dateeService;

    @Autowired
    public DateeController(DateeService dateeService) {
        this.dateeService = dateeService;
    }

    @GetMapping
    public List<Datee> getAllDatees() {
        return dateeService.getAllDatees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Datee> getDateeById(@PathVariable int id) {
        Datee datee = dateeService.getDateeById(id);
        return ResponseEntity.ok(datee);
    }

    @PostMapping
    public Datee createDatee(@RequestBody Datee datee) {
        return dateeService.saveDatee(datee);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Datee> updateDatee(@PathVariable int id, @RequestBody Datee updatedDatee) {
        Datee datee = dateeService.updateDatee(id, updatedDatee);
        return ResponseEntity.ok(datee);
    }

    @DeleteMapping("/{id}")
    public void deleteDatee(@PathVariable int id) {
        dateeService.deleteDatee(id);
    }
}
