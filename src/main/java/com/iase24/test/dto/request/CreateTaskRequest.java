package com.iase24.test.dto.request;

import com.iase24.test.entity.enumeration.TaskPriority;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Тело запроса для создания задачи")
public class CreateTaskRequest {

    @Schema(description = "Заголовок задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Приоритет задачи", example = """
            HIGH
            MEDIUM
            LOW
            """)
    private TaskPriority priority;
}
