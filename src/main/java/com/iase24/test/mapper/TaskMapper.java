package com.iase24.test.mapper;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.UpdateTaskDataDto;
import com.iase24.test.dto.request.AssigneeTaskForUserRequest;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.entity.Task;

import java.util.List;

public interface TaskMapper {

    CreatedTaskResponse entityToCreateResponse(Task task);

    Task CreateRequestToEntity(CreateTaskRequest dto);

    List<TaskDataDto> listEntityToListDto(List<Task> tasks);

    TaskDataDto entityTaskToTaskDto(Task task);

    UpdateTaskDataDto entityToUpdateDto(Task task);

    Task updateDtoToEntity(UpdateTaskDataDto dto);

    Task buildUpdateTaskForSave(Task existingTask, Task updatedTask);

    Task assigneeDtoToEntity(AssigneeTaskForUserRequest dto);

    Task buildAssigneeToSave(Task exisitingTask, Task assigneeTask);
}
