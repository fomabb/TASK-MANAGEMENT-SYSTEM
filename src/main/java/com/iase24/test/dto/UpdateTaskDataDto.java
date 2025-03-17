package com.iase24.test.dto;

import com.iase24.test.entity.enumeration.TaskPriority;
import com.iase24.test.entity.enumeration.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

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

    @Schema(description = "Время обновления")
    private LocalDateTime updatedAt;
}
