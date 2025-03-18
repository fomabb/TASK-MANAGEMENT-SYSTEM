package org.fomabb.taskmanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Getter;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;

@Getter
@Builder
@Schema(description = "Тело запроса для создания задачи")
public class CreateTaskRequest {


    @Schema(description = "Идентификатор автора", example = "1")
    private Long authorId;

    @NotBlank(message = "Название задачи не может быть пустым")
    @Size(max = 255, message = "Название задачи не должно превышать 255 символов")
    @Schema(description = "Заголовок задачи", example = "US-4.7.3 Закрытие карты")
    private String title;

    @Schema(description = "Необходимо осуществить закрытие карты.")
    private String description;

    @Schema(description = "Приоритет задачи", allowableValues = {"HIGH", "MEDIUM", "LOW"})
    private TaskPriority priority;
}
