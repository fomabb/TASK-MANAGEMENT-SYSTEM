package org.fomabb.taskmanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
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
}
