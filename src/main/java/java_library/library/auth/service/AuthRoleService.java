package java_library.library.auth.service;


import java_library.library.auth.entity.AuthRoleEntity;
import java_library.library.auth.repository.AuthRoleRepository;
import java_library.library.common.enums.AuthRoleGroup;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthRoleService {

    private final AuthRoleRepository authRoleRepository;

    public AuthRoleService(AuthRoleRepository authRoleRepository) {
        this.authRoleRepository = authRoleRepository;
    }

    public List<AuthRoleEntity> findAll() {
        return authRoleRepository.findAll();
    }

    public AuthRoleEntity findById(Long id) {
        return authRoleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("Role not found: " + id)
                );
    }

    public AuthRoleEntity findByRole(AuthRoleGroup role) {
        return authRoleRepository.findByRole(role)
                .orElseThrow(() ->
                        new RuntimeException("Role not found: " + role)
                );
    }

    public boolean existsByRole(AuthRoleGroup role) {
        return authRoleRepository.existsByRole(role);
    }

    public AuthRoleEntity save(AuthRoleEntity role) {
        return authRoleRepository.save(role);
    }
}

