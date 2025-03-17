package com.iase24.test.service.impl;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.request.UpdateTaskDataDtoRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.dto.response.UpdateTaskDataDtoResponse;
import com.iase24.test.mapper.TaskMapper;
import com.iase24.test.repository.TaskRepository;
import com.iase24.test.security.repository.UserRepository;
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
    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest body) {
        userRepository.findById(body.getAuthorId())
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(USER_WITH_ID_S_NOT_FOUND, body.getAuthorId())));
        return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.CreateRequestToEntity(body)));
    }


    @Override
    public List<TaskDataDto> getAllTask() {
        return taskMapper.listEntityToListDto(taskRepository.findAll());
    }

    @Override
    @Transactional
    public UpdateTaskDataDtoResponse updateTask(UpdateTaskDataDtoRequest dataDtoRequest) {
        return null;
    }

    @Override
    public TaskDataDto getTaskById(Long id) {
        return taskMapper.entityTaskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_WITH_ID_S_NOT_FOUND, id))));
    }
}
