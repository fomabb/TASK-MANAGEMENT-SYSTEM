package org.fomabb.taskmanagement.util.testobjectgenerator.task;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserAssigneeDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static java.time.LocalDateTime.now;

@UtilityClass
public class TaskResponseGenerator {

    public static CreateTaskRequest generateCreateTaskRequest() {
        return CreateTaskRequest.builder()
                .authorId(1L)
                .title("US-4.7.3 Закрытие карты")
                .description("Необходимо осуществить закрытие карты.")
                .priority(TaskPriority.MEDIUM)
                .build();
    }

    public static CreatedTaskResponse generateCreateTaskResponse(CreateTaskRequest request) {
        return CreatedTaskResponse.builder()
                .id(1L)
                .title(request.getTitle())
                .description(request.getDescription())
                .status(TaskStatus.PENDING)
                .priority(request.getPriority())
                .createdAt(now())
                .updatedAt(null)
                .build();
    }

    public static PageableResponse<TaskDataDto> generatePageTaskResponse(List<TaskDataDto> taskDataDtoList, List<Task> taskList) {
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
                .timeLeadTask(2)
                .build();
    }

    public static UpdateAssigneeResponse generateAssigneeTaskUpdate(Task existingTask) {
        return UpdateAssigneeResponse.builder()
                .taskId(existingTask.getId())
                .title(existingTask.getTitle())
                .updatedAt(existingTask.getUpdatedAt())
                .assigneeId(existingTask.getId())
                .timeLeadTask(existingTask.getExceedingTimeLimit())
                .build();
    }

    public static Map<String, List<TaskDataDto>> generateTaskResponseMap(
            TaskDataDto response,
            LocalDate date
    ) {
        Map<String, List<TaskDataDto>> responseMap = new LinkedHashMap<>();

        responseMap.put("MONDAY " + date.plusDays(0).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("TUESDAY " + date.plusDays(1).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("WEDNESDAY " + date.plusDays(2).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("THURSDAY " + date.plusDays(3).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("FRIDAY " + date.plusDays(4).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("SATURDAY " + date.plusDays(5).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), new ArrayList<>());
        responseMap.put("SUNDAY " + date.plusDays(6).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")), List.of(response));

        return responseMap;
    }
}
