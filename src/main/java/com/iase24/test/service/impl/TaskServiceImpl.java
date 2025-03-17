package com.iase24.test.service.impl;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.UpdateTaskDataDto;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.entity.Task;
import com.iase24.test.mapper.TaskMapper;
import com.iase24.test.repository.TaskRepository;
import com.iase24.test.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    public static final String USER_WITH_ID_S_NOT_FOUND = "User with ID %s not found";
    public static final String TASK_WITH_ID_S_NOT_FOUND = "Task with ID %s not found";
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest body) {
        getTaskById(body.getAuthorId());
        return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.CreateRequestToEntity(body)));
    }


    @Override
    public List<TaskDataDto> getAllTask() {
        return taskMapper.listEntityToListDto(taskRepository.findAll());
    }

    @Override
    @Transactional
    public UpdateTaskDataDto updateTask(UpdateTaskDataDto dataDtoRequest) {
        Task existingTask = taskRepository.findById(dataDtoRequest.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(TASK_WITH_ID_S_NOT_FOUND, dataDtoRequest.getTaskId())));
        Task updatedTask = taskMapper.updateDtoToEntity(dataDtoRequest);
        return taskMapper.entityToUpdateDto(taskRepository.save(taskMapper.buildUpdateTaskForSave(existingTask, updatedTask)));
    }

    @Override
    public TaskDataDto getTaskById(Long id) {
        return taskMapper.entityTaskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_WITH_ID_S_NOT_FOUND, id))));
    }
}
