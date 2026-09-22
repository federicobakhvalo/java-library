package java_library.library.auth.repository;


import java_library.library.auth.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {
}

