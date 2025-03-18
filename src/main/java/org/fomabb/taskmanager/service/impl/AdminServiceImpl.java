package org.fomabb.taskmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanager.dto.UserDataDto;
import org.fomabb.taskmanager.mapper.UserMapper;
import org.fomabb.taskmanager.security.repository.UserRepository;
import org.fomabb.taskmanager.service.AdminService;
import org.fomabb.taskmanager.util.ConstantProject;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AdminServiceImpl implements AdminService {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    public UserDataDto getUserById(Long userId) {
        return userMapper.entityUserToUserDto(userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.USER_WITH_ID_S_NOT_FOUND, userId)
                )));
    }

    @Override
    public List<UserDataDto> getAllUser() {
        return userMapper.listEntityUserToListUserDto(userRepository.findAll());
    }
}
