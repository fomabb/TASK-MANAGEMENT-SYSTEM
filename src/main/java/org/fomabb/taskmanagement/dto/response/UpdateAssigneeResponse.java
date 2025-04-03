package org.fomabb.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Response DTO for updating task assignee")
public class UpdateAssigneeResponse {

    @Schema(description = "ID задачи", example = "1")
    private Long taskId;

    @Schema(description = "Название задачи", example = "Task Title")
    private String title;

    @Schema(description = "Дата и время назначения исполнителя", example = "19-03-2025 19:55")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime updatedAt;

    @Schema(description = "ID исполнителя", example = "2")
    private Long assigneeId;

    @Schema(description = "Время выполнения задачи", example = "8")
    private Integer timeLeadTask;
}
