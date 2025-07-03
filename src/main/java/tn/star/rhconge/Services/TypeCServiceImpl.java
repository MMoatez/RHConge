package tn.star.rhconge.Services;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.star.rhconge.Entities.TypeC;
import tn.star.rhconge.Repositories.TypeCRepository;

import java.util.List;

@AllArgsConstructor
@Service
@Slf4j

public class TypeCServiceImpl implements TypeCService {
    @Autowired
    private TypeCRepository typeCRepository;
    @Override
    public TypeC saveTypeC(TypeC typeC) {
        return typeCRepository.save(typeC);
    }


    @Override
    public void deleteTypeC(int id) {
        typeCRepository.deleteById(id);
    }



    @Override
    public List<TypeC> getAllTypesC() {
        return typeCRepository.findAll();
    }


    @Override
    public List<TypeC> saveAllTypesC(List<TypeC> typesC) {
        return typeCRepository.saveAll(typesC);
    }




}
