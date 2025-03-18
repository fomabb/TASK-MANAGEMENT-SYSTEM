package org.fomabb.taskmanager.service;

import org.fomabb.taskmanager.dto.UserDataDto;

import java.util.List;

public interface AdminService {

    UserDataDto getUserById(Long userId);

    List<UserDataDto> getAllUser();
}
