package org.fomabb.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UpdateAssigneeResponse {

    private Long taskId;
    private String title;

    @Schema(description = "Дата и время назначения исполнителя", example = "19-03-2025 19:55")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime updatedAt;
    private Long assigneeId;
}
