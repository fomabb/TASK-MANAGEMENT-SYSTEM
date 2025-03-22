package org.fomabb.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@Schema(description = "Объект, представляющий данные задачи")
public class TaskDataDto {

    @Schema(description = "Идентификатор задачи", example = "1")
    private Long id;

    @Schema(description = "Заголовок задачи", example = "Task-1 Title")
    private String title;

    @Schema(description = "Описание задачи", example = "This is a description of Task-1.")
    private String description;

    @Schema(description = "Время создания задачи", example = "19-03-2025 17:38")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @Schema(description = "Время обновления задачи", example = "19-03-2025 19:38")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime updatedAt;

    @Schema(description = "Статус задачи", example = "PENDING, IN_PROGRESS, or COMPLETED")
    private TaskStatus status;

    @Schema(description = "Приоритет задачи", example = "LOW, MEDIUM, or HIGH")
    private TaskPriority priority;

    @Schema(description = "Автор задачи", implementation = UserAuthorDataDto.class)
    private UserAuthorDataDto author;

    @Schema(description = "Исполнитель", implementation = UserAssigneeDataDto.class)
    private UserAssigneeDataDto assignee;
}
