package org.fomabb.taskmanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Schema(description = "Запрос на обновление задачи для пользователя")
public class UpdateTaskForUserDataRequest {

    @Schema(description = "Уникальный идентификатор задачи", example = "101")
    private Long taskId;

    @Schema(description = "Статус задачи", example = "IN_PROGRESS")
    private TaskStatus taskStatus;
}
