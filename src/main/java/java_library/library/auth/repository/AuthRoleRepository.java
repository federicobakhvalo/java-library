package java_library.library.auth.repository;

import java_library.library.auth.entity.AuthRoleEntity;
import java_library.library.common.enums.AuthRoleGroup;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AuthRoleRepository extends JpaRepository<AuthRoleEntity, Long> {
    Optional<AuthRoleEntity> findByRole(AuthRoleGroup role);
}

