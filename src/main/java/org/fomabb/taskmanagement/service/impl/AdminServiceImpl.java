package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.mapper.UserMapper;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.AdminService;
import org.fomabb.taskmanagement.util.ConstantProject;
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
