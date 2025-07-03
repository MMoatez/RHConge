package tn.star.rhconge.Services;

import tn.star.rhconge.Entities.Role;

import java.util.List;
import java.util.Optional;

public interface RoleService {

    Role createRole(Role role);

    Role updateRole(Role role);

    void deleteRole(int id);

    Optional<Role> getRoleById(int id);

    List<Role> getAllRoles();

    List<Role> createRoles(List<Role> roles);

}
