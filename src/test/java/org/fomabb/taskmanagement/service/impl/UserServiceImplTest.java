package org.fomabb.taskmanagement.service.impl;

import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.mapper.UserMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class UserServiceImplTest {

    @Mock
    private UserMapper userMapper;

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserServiceImpl userService;

    @BeforeEach
    public void setUp() {

    }

    @BeforeAll
    public static void setUpBeforeAll() {

    }

    @Test
    void getUserById_ShouldReturnUserDataDto() {

    }

    @Test
    void getAllUsers_ShouldReturnListUserDataDto() {

    }

    @Test
    void updateTaskStatusForUser_ShouldReturnUpdateUpdateTaskDataDto() {

    }

    @Test
    void getUserById_ShouldThrowEntityNotFoundException() {

    }

    @Test
    void getAllUsers_ShouldReturnEmptyList() {

    }

    @Test
    void updateTaskStatusForUser_ShouldThrowEntityNotFoundException() {

    }

}
