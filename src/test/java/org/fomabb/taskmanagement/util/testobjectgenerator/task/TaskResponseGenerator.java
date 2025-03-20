package org.fomabb.taskmanagement.util.testobjectgenerator.task;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UserAssigneeDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.security.entity.User;

import java.time.LocalDateTime;
import java.util.List;

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

    public static Task generateTaskEntity() {
        return Task.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(LocalDateTime.parse("20-03-2025 12:21"))
                .updatedAt(LocalDateTime.parse("20-03-2025 15:12"))
                .assignee(User.builder().id(2L).build())
                .author(User.builder().id(1L).build())
                .build();
    }

    public static List<TaskDataDto> generateListTaskDataDto() {
        Task task1 = Task.builder()
                .id(1L)
                .title("Task-1 Title")
                .description("Task-1 Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(LocalDateTime.parse("20-03-2025 12:21"))
                .updatedAt(LocalDateTime.parse("20-03-2025 15:12"))
                .assignee(User.builder().id(2L).build())
                .author(User.builder().id(1L).build())
                .build();

        Task task2 = Task.builder()
                .id(2L)
                .title("Task-2 Title")
                .description("Task-2 Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(LocalDateTime.parse("20-03-2025 13:21"))
                .updatedAt(LocalDateTime.parse("20-03-2025 16:44"))
                .assignee(User.builder().id(2L).build())
                .author(User.builder().id(1L).build())
                .build();

        List<Task> tasks = List.of(task1, task2);

        return tasks.stream()
                .map(task -> TaskDataDto.builder()
                        .id(task.getId())
                        .title(task.getTitle())
                        .description(task.getDescription())
                        .priority(task.getPriority())
                        .status(task.getStatus())
                        .createdAt(task.getCreatedAt())
                        .updatedAt(task.getUpdatedAt())
                        .assignee(TaskDataDto.builder().build().getAssignee())
                        .author(TaskDataDto.builder().build().getAuthor())
                        .build())
                .toList();
    }
}
