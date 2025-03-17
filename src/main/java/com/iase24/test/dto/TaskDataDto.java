package com.iase24.test.dto;

import com.iase24.test.entity.enumeration.TaskPriority;
import com.iase24.test.entity.enumeration.TaskStatus;
import com.iase24.test.security.entity.User;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class TaskDataDto {

    private Long id;
    private String title;
    private String description;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private TaskStatus status;
    private TaskPriority priority;
    private List<String> comments;
    private User author;
    private User assignee;
}
