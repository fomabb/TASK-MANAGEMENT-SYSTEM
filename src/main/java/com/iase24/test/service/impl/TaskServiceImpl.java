package com.iase24.test.service.impl;

import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.mapper.TaskMapper;
import com.iase24.test.repository.TaskRepository;
import com.iase24.test.service.TaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest body) {
        return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.CreateRequestToEntity(body)));
    }
}
