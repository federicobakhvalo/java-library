
package java_library.library.auth.service;

import java_library.library.auth.entity.AuthRoleEntity;
import java_library.library.auth.entity.UserRoleEntity;
import java_library.library.auth.repository.UserRoleRepository;
import java_library.library.user.entity.UserEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserRoleService {

    private final UserRoleRepository userRoleRepository;

    public UserRoleService(UserRoleRepository userRoleRepository) {
        this.userRoleRepository = userRoleRepository;
    }

    public List<UserRoleEntity> findAll() {
        return userRoleRepository.findAll();
    }

    public List<AuthRoleEntity> findRolesByUserId(Long userId) {
        return userRoleRepository.findByUserId(userId).stream().map(UserRoleEntity::getRole).toList();
    }

    public UserRoleEntity findById(Long id) {
        return userRoleRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User role not found: " + id)
                );
    }

    public boolean exists(Long userId, Long roleId) {
        return userRoleRepository.existsByUserIdAndRoleId(userId, roleId);
    }

    public UserRoleEntity assignRole(
            UserEntity user,
            AuthRoleEntity role
    ) {
        if (exists(user.getId(), role.getId())) {
            throw new RuntimeException(
                    "Role is already assigned to this user"
            );
        }

        UserRoleEntity userRole = new UserRoleEntity();

        // Здесь пока нужны setter'ы в UserRoleEntity
        userRole.setUser(user);
        userRole.setRole(role);

        return userRoleRepository.save(userRole);
    }

    public void removeRole(Long userId, Long roleId) {
        UserRoleEntity userRole = userRoleRepository
                .findByUserIdAndRoleId(userId, roleId)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User does not have this role"
                        )
                );

        userRoleRepository.delete(userRole);
    }
}

