package org.fomabb.taskmanager.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Ответ на добавление комментария к задаче")
public class CommentAddedResponse {

    @Schema(description = "ID добавленного комментария", example = "1")
    private Long commentId;

    @Schema(description = "ID задачи, к которой добавлен комментарий", example = "101")
    private Long taskId;

    @Schema(description = "Содержимое комментария", example = "Это мой комментарий к задаче.")
    private String content;

    @Schema(description = "Имя автора комментария", example = "Иван")
    private String authorFirstName;

    @Schema(description = "Фамилия автора комментария", example = "Иванов")
    private String authorLastName;

    @Schema(description = "Дата и время последнего обновления комментария", example = "2025-03-18T12:34:56")
    private LocalDateTime updateComment;
}
