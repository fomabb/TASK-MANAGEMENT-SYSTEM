package com.iase24.test.dto.response;

import com.iase24.test.entity.enumeration.TaskPriority;
import com.iase24.test.entity.enumeration.TaskStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Ответ данных о задаче")
public class CreatedTaskResponse {

    @Schema(description = "Идентификатор задачи")
    private Long id;

    @Schema(description = "Заголовок задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Статус задачи", example = """
            `PENDING`
            `IN_PROGRESS`
            `COMPLETED`
            """)
    private TaskStatus status;

    @Schema(description = "Приоритет задачи", example = """
            HIGH
            MEDIUM
            LOW
            """)
    private TaskPriority priority;

    @Schema(description = "Время создания задачи")
    private LocalDateTime createdAt;

    @Schema(description = "Время обновления задачи")
    private LocalDateTime updatedAt;
}
