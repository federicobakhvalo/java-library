
package java_library.library.user.mapper;

import org.springframework.stereotype.Component;

import java_library.library.user.dto.request.UserCreateRequest;
import java_library.library.user.dto.response.UserResponse;
import java_library.library.user.entity.UserEntity;

@Component
public class UserMapper {

    public UserEntity toEntity(UserCreateRequest request) {
        UserEntity user = new UserEntity();

        user.setUsername(request.getUsername());
        user.setPassword(request.getPassword());
        user.setEmail(request.getEmail());
        user.setBirthDate(request.getBirthDate());
        user.setAvatarUrl(request.getAvatarUrl());

        return user;
    }

    public UserResponse toResponse(UserEntity user) {
        UserResponse response = new UserResponse();

        response.setId(user.getId());
        response.setUsername(user.getUsername());
        response.setEmail(user.getEmail());
        response.setIsActive(user.getIsActive());
        response.setBirthDate(user.getBirthDate());
        response.setCreatedAt(user.getCreatedAt());
        response.setAvatarUrl(user.getAvatarUrl());

        return response;
    }
}

