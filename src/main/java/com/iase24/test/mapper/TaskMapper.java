package com.iase24.test.mapper;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.entity.Task;

import java.util.List;

public interface TaskMapper {

    CreatedTaskResponse entityToCreateResponse(Task task);

    Task CreateRequestToEntity(CreateTaskRequest request);

    List<TaskDataDto> listEntityToListDto(List<Task> tasks);

    TaskDataDto entityTaskToTaskDto(Task task);
}
