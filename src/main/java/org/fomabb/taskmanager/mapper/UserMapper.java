package org.fomabb.taskmanager.mapper;

import org.fomabb.taskmanager.dto.UserDataDto;
import org.fomabb.taskmanager.security.entity.User;

import java.util.List;

public interface UserMapper {

    UserDataDto entityUserToUserDto(User user);

    List<UserDataDto> listEntityUserToListUserDto(List<User> users);
}
