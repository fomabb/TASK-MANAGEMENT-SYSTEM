package com.iase24.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Объект передачи данных идентификатора исполнителя задачи")
public class UserAssigneeDataDto {

    @Schema(description = "Идентификатор исполнителя", example = "2")
    private Long assigneeId;
}
