package org.fomabb.taskmanagement.util.testobjectgenerator.task;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserAssigneeDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;

import java.util.ArrayList;
import java.util.List;

import static java.time.LocalDateTime.now;

@UtilityClass
public class TaskResponseGenerator {

    public static PageableResponse<TaskDataDto> generatePageTaskResponse(List<TaskDataDto> taskDataDtoList, List<Task> taskList) {
        // Если сервис конвертирует 0-based → 1-based
        return PageableResponse.<TaskDataDto>builder()
                .content(taskDataDtoList)
                .pageNumber(1) // Если сервис конвертирует 0-based → 1-based
                .pageSize(10)
                .isFirst(true)
                .isLast(true)
                .numberOfElements(taskList.size())
                .isEmpty(false)
                .totalPages(1)
                .totalItems(taskList.size())
                .build();
    }

    public static TaskDataDto generateTaskDataDto() {
        return TaskDataDto.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(now())
                .updatedAt(null)
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
                .createdAt(now())
                .comments(new ArrayList<>())
                .updatedAt(null)
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
                .createdAt(now())
                .updatedAt(null)
                .assignee(User.builder().id(2L).build())
                .author(User.builder().id(1L).build())
                .build();

        Task task2 = Task.builder()
                .id(2L)
                .title("Task-2 Title")
                .description("Task-2 Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(now())
                .updatedAt(null)
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

    public static List<Task> generateListTaskEntity() {
        Task task1 = Task.builder()
                .id(1L)
                .title("Task-1 Title")
                .description("Task-1 Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(now())
                .comments(new ArrayList<>())
                .updatedAt(null)
                .assignee(User.builder().id(2L).build())
                .author(User.builder().id(1L).build())
                .build();

        Task task2 = Task.builder()
                .id(2L)
                .title("Task-2 Title")
                .description("Task-2 Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.IN_PROGRESS)
                .createdAt(now())
                .comments(new ArrayList<>())
                .updatedAt(null)
                .assignee(User.builder().id(2L).build())
                .author(User.builder().id(1L).build())
                .build();

        return List.of(task1, task2);
    }

    public static UpdateTaskDataDto generateEntityTaskToUpdateTaskDto(Task task) {
        return UpdateTaskDataDto.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
    }

    public static AssigneeTaskForUserRequest generateAssigneeTaskRequest(Task existingTask) {
        return AssigneeTaskForUserRequest.builder()
                .taskId(existingTask.getId())
                .assigneeId(existingTask.getId())
                .build();
    }

    public static UpdateAssigneeResponse generateAssigneeTaskUpdate(Task existingTask) {
        return UpdateAssigneeResponse.builder()
                .taskId(existingTask.getId())
                .title(existingTask.getTitle())
                .updatedAt(existingTask.getUpdatedAt())
                .assigneeId(existingTask.getId())
                .build();
    }
}
