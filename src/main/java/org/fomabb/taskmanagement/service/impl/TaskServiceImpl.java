package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.PaginTaskResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.exceptionhandler.exeption.BusinessException;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.TaskService;
import org.fomabb.taskmanagement.util.ConstantProject;
import org.fomabb.taskmanagement.util.paging.PagingResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

//==================================SECTION~ADMIN=======================================================================

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest requestBody) {
        Optional<User> user = userRepository.findById(requestBody.getAuthorId());

        if (user.isPresent()) {
            return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.createRequestToEntity(requestBody)));
        }
        throw new EntityNotFoundException(String.format(
                ConstantProject.USER_WITH_ID_S_NOT_FOUND, requestBody.getAuthorId()));
    }

    @Override
    public PaginTaskResponse getAllTasks(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(taskPage.getContent());
        return PagingResponseUtil.buildPagingResponse(taskDataDtos, taskPage, new PaginTaskResponse());
    }

    @Override
    @Transactional
    public UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto requestBody) {
        Task existingTask = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId()))
                );
        Task updatedTask = taskMapper.updateDtoToEntity(requestBody);
        return taskMapper.entityToUpdateDto(taskRepository
                .save(taskMapper.buildUpdateTaskForSave(existingTask, updatedTask)));
    }

    @Override
    public TaskDataDto getTaskById(Long id) {
        return taskMapper.entityTaskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, id))));
    }

    @Override
    @Transactional
    public void removeTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignTaskPerformers(AssigneeTaskForUserRequest requestBody) {
        Task existingTask = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId()))
                );
        userRepository.findById(requestBody.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        ConstantProject.USER_WITH_ID_S_NOT_FOUND, requestBody.getAssigneeId()))
                );
        taskRepository.save(taskMapper.buildAssigneeToSave(existingTask, taskMapper.assigneeDtoToEntity(requestBody)));
    }

    @Override
    public boolean existsByTitle(String title) {
        return taskRepository.existsByTitle(title);
    }

//==================================SECTION~USER========================================================================

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
