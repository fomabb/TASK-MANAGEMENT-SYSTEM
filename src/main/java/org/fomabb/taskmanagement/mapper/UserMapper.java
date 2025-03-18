package org.fomabb.taskmanagement.mapper;

import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.security.entity.User;

import java.util.List;

public interface UserMapper {

    UserDataDto entityUserToUserDto(User user);

    List<UserDataDto> listEntityUserToListUserDto(List<User> users);
}
