package tn.star.rhconge.Controllers;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.star.rhconge.Entities.TypeC;
import tn.star.rhconge.Repositories.TypeCRepository;
import tn.star.rhconge.Services.TypeCService;
import tn.star.rhconge.exception.ResourceNotFoundException;

import java.util.List;

@RestController
@RequestMapping("/typec")
public class TypeCController {

    @Autowired
    private TypeCService typeCService;
    @Autowired
    private TypeCRepository typeCRepository;


    @GetMapping("")
    public List<TypeC> getAllTypeC(){
        return typeCService.getAllTypesC();
    }

    @PostMapping("/ajouterTypesC")
    public List<TypeC> createTypeC(@RequestBody List<TypeC> typesC){
        return typeCService.saveAllTypesC(typesC);
    }

    @DeleteMapping("/supprimerTypesC/{id}")
    public void deleteTypeC(@PathVariable int id) { typeCService.deleteTypeC(id); }


    //Update
    @PutMapping("{id}")
    public ResponseEntity<TypeC> updateTypeC(@PathVariable long id, @RequestBody TypeC typeC) {
        TypeC updateTypeC = typeCRepository.findById((int) id)
                .orElseThrow(() -> new ResourceNotFoundException("Type Conge not exist with id: " + id));

        updateTypeC.setName(typeC.getName());

        typeCRepository.save(updateTypeC);

        return ResponseEntity.ok(updateTypeC);
    }

}
