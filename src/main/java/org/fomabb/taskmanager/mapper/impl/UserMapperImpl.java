package org.fomabb.taskmanager.mapper.impl;

import org.fomabb.taskmanager.dto.UserDataDto;
import org.fomabb.taskmanager.mapper.UserMapper;
import org.fomabb.taskmanager.security.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
public class UserMapperImpl implements UserMapper {
    @Override
    public UserDataDto entityUserToUserDto(User user) {
        return UserDataDto.builder()
                .userId(user.getId())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .build();
    }

    @Override
    public List<UserDataDto> listEntityUserToListUserDto(List<User> users) {

        if (users == null) {
            return Collections.emptyList();
        }

        return users.stream().map(this::entityUserToUserDto).toList();
    }
}
