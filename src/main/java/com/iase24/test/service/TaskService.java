package com.iase24.test.service;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.request.UpdateTaskDataDtoRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.dto.response.UpdateTaskDataDtoResponse;

import java.util.List;

public interface TaskService {

    CreatedTaskResponse createTask(CreateTaskRequest body);

    List<TaskDataDto> getAllTask();

    UpdateTaskDataDtoResponse updateTask(UpdateTaskDataDtoRequest dataDtoRequest);

    TaskDataDto getTaskById(Long id);
}