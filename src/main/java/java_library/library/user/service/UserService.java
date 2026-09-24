
package java_library.library.user.service;

import java_library.library.user.entity.UserEntity;
import java_library.library.user.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() ->
                        new RuntimeException("User not found: " + id)
                );
    }

    public UserEntity findByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + username
                        )
                );
    }

    public UserEntity findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + email
                        )
                );
    }

    public UserEntity findByUsernameOrEmail(String login) {
        return userRepository.findByUsernameOrEmail(login, login)
                .orElseThrow(() ->
                        new RuntimeException(
                                "User not found: " + login
                        )
                );
    }

    public boolean existsByUsername(String username) {
        return userRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public UserEntity create(UserEntity user) {

        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException(
                    "Username already exists: " + user.getUsername()
            );
        }

        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException(
                    "Email already exists: " + user.getEmail()
            );
        }

        return userRepository.save(user);
    }
}
