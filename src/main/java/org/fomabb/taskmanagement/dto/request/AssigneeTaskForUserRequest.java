package org.fomabb.taskmanagement.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Тело запроса для установки исполнителя задачи")
public class AssigneeTaskForUserRequest {

    @Schema(description = "Идентификатор автор", example = "1")
    private Long taskId;

    @Schema(description = "Идентификатор исполнителя", example = "2")
    private Long assigneeId;

    @Schema(description = "Время выполнения задачи", example = "8")
    @NotNull(message = "Time field it cannot be empty")
    private Integer timeLeadTask;
}
