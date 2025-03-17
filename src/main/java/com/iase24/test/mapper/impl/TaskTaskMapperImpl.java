package com.iase24.test.mapper.impl;

import com.iase24.test.dto.CommentsDataDto;
import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.UserAssigneeDataDto;
import com.iase24.test.dto.UserAuthorDataDto;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.entity.Task;
import com.iase24.test.entity.enumeration.TaskStatus;
import com.iase24.test.mapper.TaskMapper;
import com.iase24.test.security.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public Task CreateRequestToEntity(CreateTaskRequest request) {
        return Task.builder()
                .author(User.builder().id(request.getAuthorId()).build())
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
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
                .comments(task.getComments().stream()
                        .map(comment -> CommentsDataDto.builder()
                                .id(comment.getId())
                                .text(comment.getContent())
                                .author(UserAuthorDataDto.builder().authorId(comment.getAuthor().getId()).build())
                                .build())
                        .collect(Collectors.toList()))
                .assignee(task.getAssignee() != null ?
                        UserAssigneeDataDto.builder().assigneeId(task.getAssignee().getId()).build() : null)
                .author(UserAuthorDataDto.builder().authorId(task.getAuthor().getId()).build())
                .build();
    }
}