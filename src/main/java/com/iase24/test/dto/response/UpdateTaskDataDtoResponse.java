package com.iase24.test.dto.response;

import com.iase24.test.entity.enumeration.TaskPriority;
import com.iase24.test.entity.enumeration.TaskStatus;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateTaskDataDtoResponse {

    private Long taskId;
    private String title;
    private String description;
    private TaskStatus status;
    private TaskPriority priority;
    private LocalDateTime updatedAt;
}
