package java_library.library.auth.repository;


import java_library.library.auth.entity.UserRoleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

public interface UserRoleRepository extends JpaRepository<UserRoleEntity, Long> {

    boolean existsByUserIdAndRoleId(Long userId, Long roleId);

    Optional<UserRoleEntity> findByUserIdAndRoleId(Long userId, Long roleId);

    List<UserRoleEntity> findByUserId(Long userId);

}

