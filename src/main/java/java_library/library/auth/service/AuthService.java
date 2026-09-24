
package java_library.library.auth.service;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import java_library.library.auth.dto.response.JwtUserData;
import java_library.library.auth.dto.request.RegisterRequest;
import java_library.library.auth.dto.response.AuthResponse;
import java_library.library.auth.entity.AuthRoleEntity;
import java_library.library.common.enums.AuthRoleGroup;
import java_library.library.auth.security.JwtService;
import java_library.library.auth.service.AuthRoleService;
import java_library.library.auth.service.UserRoleService;
import java_library.library.user.entity.UserEntity;
import java_library.library.user.mapper.UserMapper;
import java_library.library.user.service.UserService;
import java_library.library.auth.dto.request.LoginRequest;

@Service
public class AuthService {

    private final UserService userService;
    private final UserMapper userMapper;
    private final AuthRoleService authRoleService;
    private final UserRoleService userRoleService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    public AuthService(
            UserService userService,
            UserMapper userMapper,
            AuthRoleService authRoleService,
            UserRoleService userRoleService,
            PasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userService = userService;
        this.userMapper = userMapper;
        this.authRoleService = authRoleService;
        this.userRoleService = userRoleService;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        if (userService.existsByUsername(request.getUsername())) {
            throw new RuntimeException("Username already exists");
        }

        if (userService.existsByEmail(request.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

//        if (userService.findByUsername(request.getUsername()) != null) {
//            throw new RuntimeException(
//                    "Username already exists: " + request.getUsername()
//            );
//        }
//
//        if (userService.findByEmail(request.getEmail()) != null) {
//            throw new RuntimeException(
//                    "Email already exists: " + request.getEmail()
//            );
//        }

        UserEntity user = new UserEntity();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setEmail(request.getEmail());
        user.setBirthDate(request.getBirthDate());
        user.setAvatarUrl(request.getAvatarUrl());
        UserEntity savedUser = userService.create(user);

        AuthRoleEntity userRole =
                authRoleService.findByRole(AuthRoleGroup.USER);

        userRoleService.assignRole(
                savedUser,
                userRole
        );

        List<String> roles = userRoleService
                .findRolesByUserId(savedUser.getId())
                .stream()
                .map(role -> role.getRole().name())
                .toList();

        JwtUserData jwtUserData = new JwtUserData(
                savedUser.getId(),
                savedUser.getUsername(),
                roles
        );

        String accessToken =
                jwtService.generateAccessToken(jwtUserData);

        String refreshToken =
                jwtService.generateRefreshToken(savedUser.getId());

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }

    @Transactional(readOnly = true)
    public AuthResponse login(LoginRequest request) {

        UserEntity user = userService.findByUsernameOrEmail(request.getLogin());

        if (!Boolean.TRUE.equals(user.getIsActive())) {
            throw new RuntimeException("User account is inactive");
        }

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword()
        )) {
            throw new RuntimeException("Invalid login or password");
        }

        List<String> roles = userRoleService.findRolesByUserId(user.getId())
                .stream()
                .map(role -> role.getRole().name())
                .toList();

        JwtUserData jwtUserData = new JwtUserData(
                user.getId(),
                user.getUsername(),
                roles
        );

        String accessToken = jwtService.generateAccessToken(jwtUserData);
        String refreshToken = jwtService.generateRefreshToken(user.getId());

        AuthResponse response = new AuthResponse();
        response.setAccessToken(accessToken);
        response.setRefreshToken(refreshToken);

        return response;
    }


}

