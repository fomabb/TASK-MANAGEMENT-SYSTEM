package org.fomabb.taskmanagement.util.testobjectgenerator.task;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UserAssigneeDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;

import java.time.LocalDateTime;

@UtilityClass
public class TaskResponseGenerator {

    public static TaskDataDto generateTaskDataDto() {
        return TaskDataDto.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(LocalDateTime.parse("20-03-2025 12:21"))
                .updatedAt(LocalDateTime.parse("20-03-2025 15:12"))
                .assignee(UserAssigneeDataDto.builder().assigneeId(2L).build())
                .author(UserAuthorDataDto.builder().authorId(1L).build())
                .build();
    }
}
