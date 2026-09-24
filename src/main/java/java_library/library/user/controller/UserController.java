package java_library.library.user.controller;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java_library.library.auth.dto.response.JwtUserData;
import java_library.library.user.dto.response.UserResponse;
import java_library.library.user.entity.UserEntity;
import java_library.library.user.mapper.UserMapper;
import java_library.library.user.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserController(UserService userService, UserMapper userMapper) {
        this.userService = userService;
        this.userMapper = userMapper;
    }


    @GetMapping("/{id}")
    public UserResponse getUserById(@PathVariable Long id) {
        UserEntity user = userService.findById(id);
        return userMapper.toResponse(user);
    }

    @GetMapping("/me")
    public UserResponse getCurrentUser(Authentication authentication) {
        JwtUserData userData = (JwtUserData) authentication.getPrincipal();
        UserEntity user = userService.findById(userData.getUserId());
        return userMapper.toResponse(user);
    }

    @GetMapping
    public List<UserResponse> getAllUsers() {
        return userService.findAll().stream().map(userMapper::toResponse).toList();
    }
}