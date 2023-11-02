package StoreManagement.userManagement.role;

import StoreManagement.exceptions.customExceptions.ResourceNotFoundException;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    // Retrieves all roles.
    public List<Role> getRoles() {
        List<Role> roles = roleRepository.findAll(Sort.by(Sort.Direction.ASC, "roleId"));
        if (roles.isEmpty())
            throw new ResourceNotFoundException("No roles found.");

        return roles;
    }

    // Retrieves a role by id.
    public Role getRoleById(Short roleId) {
        return roleRepository.findById(roleId)
                .orElseThrow(() -> new ResourceNotFoundException("Role not found."));
    }

}
