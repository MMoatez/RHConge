package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.TypeC;
import java.util.List;

public interface TypeCService {

    TypeC saveTypeC(TypeC typeC);
    void deleteTypeC(int id);
    List<TypeC> getAllTypesC();

    List<TypeC> saveAllTypesC(List<TypeC> typesC);

}
