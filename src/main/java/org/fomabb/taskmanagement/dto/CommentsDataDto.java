package org.fomabb.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Объект передачи данных комментариев к задаче")
public class CommentsDataDto {

    @Schema(description = "Идентификатор комментария", example = "1")
    private Long id;

    @Schema(description = "Контент комментария")
    private String text;

    @Schema(description = "Автор комментария", example = "1")
    private UserAuthorDataDto author;

    @Schema(description = "Время создания комментария", example = "19-03-2025 19:22")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timeCreated;

    @Schema(description = "Время обновления комментария", example = "19-03-2025 19:44")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime timeUpdated;
}
