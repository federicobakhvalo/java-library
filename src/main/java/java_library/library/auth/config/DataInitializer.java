
package java_library.library.auth.config;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java_library.library.auth.entity.AuthRoleEntity;
import java_library.library.common.enums.AuthRoleGroup;
import java_library.library.auth.repository.AuthRoleRepository;

@Component
public class DataInitializer implements CommandLineRunner {

    private final AuthRoleRepository authRoleRepository;

    public DataInitializer(AuthRoleRepository authRoleRepository) {
        this.authRoleRepository = authRoleRepository;
    }

    @Override
    public void run(String... args) {
        createRoleIfNotExists(AuthRoleGroup.USER);
        createRoleIfNotExists(AuthRoleGroup.ADMIN);
        createRoleIfNotExists(AuthRoleGroup.MODERATOR);
    }

    private void createRoleIfNotExists(AuthRoleGroup role) {

        if (authRoleRepository.existsByRole(role)) {
            return;
        }

        AuthRoleEntity authRole = new AuthRoleEntity();
        authRole.setRole(role);

        authRoleRepository.save(authRole);
    }
}

