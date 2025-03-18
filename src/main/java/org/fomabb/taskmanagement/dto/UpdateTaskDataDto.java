package org.fomabb.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Объект передачи данных для обновления таски")
public class UpdateTaskDataDto {

    @Schema(description = "Идентификатор задачи", example = "1")
    private Long taskId;

    @Schema(description = "Описание задачи", example = "Описание обновлено")
    private String title;

    @Schema(description = "Описание задачи", example = "Описание обновлено")
    private String description;

    @Schema(description = "Статус задачи", example = "Статус обновлен")
    private TaskStatus status;

    @Schema(description = "Приоритет задачи", example = "Приоритет обновлен")
    private TaskPriority priority;

    @Schema(description = "Дата и время обновления задачи", example = "2023-03-18T12:00:00")
    private LocalDateTime updateTask;
}
