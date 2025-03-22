package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.mapper.UserMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.security.service.UserServiceSecurity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateEntityTaskToUpdateTaskDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateListUserDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateListUsers;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
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

    @Mock
    private UserServiceSecurity userServiceSecurity;

    @InjectMocks
    private UserServiceImpl userService;

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
        List<User> users = generateListUsers();

        List<UserDataDto> expectedResponse = generateListUserDto();

        when(userMapper.listEntityUserToListUserDto(any())).thenReturn(expectedResponse);
        when(userRepository.findAll()).thenReturn(users);

        // Act
        List<UserDataDto> response = userService.getAllUsers();

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.size(), response.size());
        assertEquals(expectedResponse, response);
        verify(userRepository, times(1)).findAll();
        verify(userMapper, times(1)).listEntityUserToListUserDto(users);

    }

    @Test
    void updateTaskStatusForUser_ShouldReturnUpdateUpdateTaskDataDto() {
        // Arrange
        User currentUser = generateUserEntity();
        currentUser.setId(1L);

        Task existingTask = generateTaskEntity();
        existingTask.setAssignee(currentUser);

        UpdateTaskDataDto expectedResponse = generateEntityTaskToUpdateTaskDto(existingTask);

        UpdateTaskForUserDataRequest requestBody = UpdateTaskForUserDataRequest
                .builder().userId(currentUser.getId()).taskId(existingTask.getId())
                .taskStatus(TaskStatus.COMPLETED).build();

        when(taskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);
        when(taskRepository.save(existingTask)).thenReturn(existingTask);
        when(taskMapper.entityToUpdateDto(existingTask)).thenReturn(expectedResponse);

        // Act
        UpdateTaskDataDto response = userService.updateTaskStatusForUser(requestBody);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getTaskId(), response.getTaskId());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        verify(taskRepository, times(1)).findById(requestBody.getTaskId());
        verify(taskRepository, times(1)).save(existingTask);
    }
}
