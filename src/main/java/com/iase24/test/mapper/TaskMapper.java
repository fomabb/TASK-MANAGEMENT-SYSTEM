package com.iase24.test.mapper;

import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.entity.Task;
import com.iase24.test.entity.enumeration.TaskPriority;
import com.iase24.test.entity.enumeration.TaskStatus;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class TaskMapper implements Mapper {

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
                .title(request.getTitle())
                .description(request.getDescription())
                .priority(request.getPriority())
                .createdAt(now())
                .status(TaskStatus.PENDING)
                .updatedAt(null)
                .build();
    }
}