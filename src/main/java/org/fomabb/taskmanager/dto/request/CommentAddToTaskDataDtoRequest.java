package org.fomabb.taskmanager.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Данные для добавления комментария к задаче")
public class CommentAddToTaskDataDtoRequest {

    @Schema(description = "ID задачи, к которой добавляется комментарий", example = "101")
    private Long taskId;

    @Schema(description = "ID автора комментария", example = "1")
    private Long authorId;

    @Schema(description = "Содержимое комментария", example = "Это мой комментарий к задаче.")
    private String content;
}