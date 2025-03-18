package org.fomabb.taskmanager.mapper;

import org.fomabb.taskmanager.dto.TaskDataDto;
import org.fomabb.taskmanager.dto.UpdateTaskDataDto;
import org.fomabb.taskmanager.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanager.dto.request.CreateTaskRequest;
import org.fomabb.taskmanager.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanager.entity.Task;

import java.util.List;

public interface TaskMapper {

    CreatedTaskResponse entityToCreateResponse(Task task);

    Task createRequestToEntity(CreateTaskRequest dto);

    List<TaskDataDto> listEntityToListDto(List<Task> tasks);

    TaskDataDto entityTaskToTaskDto(Task task);

    UpdateTaskDataDto entityToUpdateDto(Task task);

    Task updateDtoToEntity(UpdateTaskDataDto dto);

    Task buildUpdateTaskForSave(Task existingTask, Task updatedTask);

    Task assigneeDtoToEntity(AssigneeTaskForUserRequest dto);

    Task buildAssigneeToSave(Task exisitingTask, Task assigneeTask);
}
