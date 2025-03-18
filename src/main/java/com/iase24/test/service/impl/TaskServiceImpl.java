package com.iase24.test.service.impl;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.UpdateTaskDataDto;
import com.iase24.test.dto.request.AssigneeTaskForUserRequest;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.entity.Task;
import com.iase24.test.mapper.TaskMapper;
import com.iase24.test.repository.TaskRepository;
import com.iase24.test.security.entity.User;
import com.iase24.test.security.repository.UserRepository;
import com.iase24.test.service.TaskService;
import com.iase24.test.util.TaskConstant;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest requestBody) {
        Optional<User> user = userRepository.findById(requestBody.getAuthorId());

        if (user.isPresent()) {
            return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.CreateRequestToEntity(requestBody)));
        }
        throw new EntityNotFoundException(String.format(
                TaskConstant.USER_WITH_ID_S_NOT_FOUND, requestBody.getAuthorId()));
    }

    @Override
    public List<TaskDataDto> getAllTask() {
        return taskMapper.listEntityToListDto(taskRepository.findAll());
    }

    @Override
    @Transactional
    public UpdateTaskDataDto updateTask(UpdateTaskDataDto requestBody) {
        Task existingTask = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(TaskConstant.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId()))
                );
        Task updatedTask = taskMapper.updateDtoToEntity(requestBody);
        return taskMapper.entityToUpdateDto(taskRepository
                .save(taskMapper.buildUpdateTaskForSave(existingTask, updatedTask)));
    }

    @Override
    public TaskDataDto getTaskById(Long id) {
        return taskMapper.entityTaskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(TaskConstant.TASK_WITH_ID_S_NOT_FOUND, id))));
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
                        String.format(TaskConstant.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId()))
                );
        userRepository.findById(requestBody.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        TaskConstant.USER_WITH_ID_S_NOT_FOUND, requestBody.getAssigneeId()))
                );
        taskRepository.save(taskMapper.buildAssigneeToSave(existingTask, taskMapper.assigneeDtoToEntity(requestBody)));
    }

    @Override
    public boolean existsByTitle(String title) {
        return taskRepository.existsByTitle(title);
    }
}
