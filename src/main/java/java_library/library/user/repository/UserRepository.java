
package java_library.library.user.repository;

import java_library.library.user.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    Optional<UserEntity> findByUsername(String username);

    Optional<UserEntity> findByEmail(String email);

    Optional<UserEntity> findByUsernameOrEmail(String username, String email);


//    Optional<UserEntity> findByLogin(String login);


    boolean existsByUsername(String username);

    boolean existsByEmail(String email);

    boolean existsByUsernameOrEmail(String username, String email);

//    boolean existsByLogin(String login);
}

