package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.UserDataDto;

import java.util.List;

public interface AdminService {

    UserDataDto getUserById(Long userId);

    List<UserDataDto> getAllUser();
}
