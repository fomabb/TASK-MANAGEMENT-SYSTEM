package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.exceptionhandler.exeption.BusinessException;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.mapper.UserMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.UserService;
import org.fomabb.taskmanagement.util.ConstantProject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserMapper userMapper;
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
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

    @Override
    @Transactional
    public UpdateTaskDataDto updateTaskStatusForUser(UpdateTaskForUserDataRequest requestBody) {
        Task taskToSave = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId())
                ));

        Long currentUserId = getCurrentUserId();

        // Проверка, является ли текущий пользователь исполнителем задачи
        if (Objects.equals(taskToSave.getAssignee().getId(), currentUserId)) {
            taskToSave.setStatus(requestBody.getTaskStatus());
            taskToSave.setUpdatedAt(now());
            return taskMapper.entityToUpdateDto(taskRepository.save(taskToSave));
        } else {
            throw new AccessDeniedException("User does not have permission to update this task.");
        }
    }

    /**
     * Извлечение ID пользователя из security context
     *
     * @return userId
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.getPrincipal() instanceof UserDetails userDetails) {
            return ((User) userDetails).getId();
        }
        throw new BusinessException("Authentication principal is not of type UserDetails");
    }
}
