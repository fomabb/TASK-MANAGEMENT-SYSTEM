package org.fomabb.taskmanagement.mapper;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.entity.Task;

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

//    PaginTaskResponse buildPagingTaskResponse(List<TaskDataDto> TaskDataDto, Page<Task> tasksPage);
}
