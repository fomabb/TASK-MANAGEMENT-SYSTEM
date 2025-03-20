package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.mapper.UserMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

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
        // Arrange
        UserDataDto expectedUserDataDto = generateUserDataDto();
        User user = new User();

        when(userRepository.findById(expectedUserDataDto.getUserId())).thenReturn(Optional.of(user));
        when(userMapper.entityUserToUserDto(user)).thenReturn(expectedUserDataDto);

        // Act
        UserDataDto actualUserDataDto = userService.getUserById(expectedUserDataDto.getUserId());

        // Assert
        assertEquals(expectedUserDataDto, actualUserDataDto);
    }

    @Test
    void getUserById_ShouldThrowEntityNotFoundException_WhenUserNotFound() {
        // Arrange
        UserDataDto user = generateUserDataDto();
        when(userRepository.findById(user.getUserId())).thenReturn(Optional.empty());

        // Act & Assert
        assertThrows(EntityNotFoundException.class, () -> userService.getUserById(user.getUserId()));
    }

    @Test
    void getAllUsers_ShouldReturnListUserDataDto() {
        // Arrange

        // Act

        // Assert

    }

    @Test
    void updateTaskStatusForUser_ShouldReturnUpdateUpdateTaskDataDto() {
        // Arrange

        // Act

        // Assert

    }

    @Test
    void getUserById_ShouldThrowEntityNotFoundException() {
        // Arrange

        // Act

        // Assert

    }

    @Test
    void getAllUsers_ShouldReturnEmptyList() {
        // Arrange

        // Act

        // Assert
    }

    @Test
    void updateTaskStatusForUser_ShouldThrowEntityNotFoundException() {
        // Arrange

        // Act

        // Assert

    }

}
