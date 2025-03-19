package org.fomabb.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Объект передачи данных задачи")
public class CreatedTaskResponse {

    @Schema(description = "Идентификатор задачи")
    private Long id;

    @Schema(description = "Заголовок задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Статус задачи", example = "PENDING or IN_PROGRESS or COMPLETED")
    private TaskStatus status;

    @Schema(description = "Приоритет задачи", example = "HIGH or MEDIUM or LOW")
    private TaskPriority priority;

    @Schema(description = "Время создания задачи", example = "19-03-2025 19:57")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @Schema(description = "Время обновления задачи", example = "19-03-2025 20:01")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime updatedAt;
}
