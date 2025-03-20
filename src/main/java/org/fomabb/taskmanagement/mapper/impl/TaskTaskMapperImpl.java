package org.fomabb.taskmanagement.mapper.impl;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserAssigneeDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.security.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.now;

@Component
public class TaskTaskMapperImpl implements TaskMapper {

    @Override
    public CreatedTaskResponse entityToCreateResponse(Task task) {
        return CreatedTaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    @Override
    public Task createRequestToEntity(CreateTaskRequest dto) {
        return Task.builder()
                .author(User.builder().id(dto.getAuthorId()).build())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .createdAt(now())
                .status(TaskStatus.PENDING)
                .updatedAt(null)
                .build();
    }

    @Override
    public List<TaskDataDto> listEntityToListDto(List<Task> tasks) {
        if (tasks == null) {
            return Collections.emptyList();
        }

        return tasks.stream()
                .map(this::entityTaskToTaskDto)
                .toList();
    }

    @Override
    public TaskDataDto entityTaskToTaskDto(Task task) {
        return TaskDataDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .assignee(task.getAssignee() != null ?
                        UserAssigneeDataDto.builder().assigneeId(task.getAssignee().getId()).build() : null)
                .author(UserAuthorDataDto.builder().authorId(task.getAuthor().getId()).build())
                .build();
    }

    @Override
    public UpdateTaskDataDto entityToUpdateDto(Task task) {
        return UpdateTaskDataDto.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
    }

    @Override
    public Task updateDtoToEntity(UpdateTaskDataDto dto) {
        return Task.builder()
                .id(dto.getTaskId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .updatedAt(now())
                .build();
    }

    @Override
    public Task assigneeDtoToEntity(AssigneeTaskForUserRequest dto) {
        return Task.builder()
                .id(dto.getTaskId())
                .assignee(User.builder().id(dto.getAssigneeId()).build())
                .build();
    }


    @Override
    public Task buildUpdateTaskForSave(Task existingTask, Task updatedTask) {
        return Task.builder()
                .id(existingTask.getId())
                .createdAt(existingTask.getCreatedAt())
                .author(existingTask.getAuthor())
                .assignee(existingTask.getAssignee())
                .comments(existingTask.getComments())
                .title(updatedTask.getTitle() != null ? updatedTask.getTitle() : existingTask.getTitle())
                .description(updatedTask.getDescription() != null ? updatedTask.getDescription() : existingTask.getDescription())
                .status(updatedTask.getStatus() != null ? updatedTask.getStatus() : existingTask.getStatus())
                .priority(updatedTask.getPriority() != null ? updatedTask.getPriority() : existingTask.getPriority())
                .updatedAt(now())
                .build();
    }

    @Override
    public Task buildAssigneeToSave(Task existingTask, Task assigneeTask) {
        return Task.builder()
                .id(existingTask.getId())
                .title(existingTask.getTitle())
                .description(existingTask.getDescription())
                .author(existingTask.getAuthor())
                .status(existingTask.getStatus())
                .priority(existingTask.getPriority())
                .createdAt(existingTask.getCreatedAt())
                .updatedAt(now())
                .author(existingTask.getAuthor())
                .assignee(assigneeTask.getAssignee())
                .build();
    }
}