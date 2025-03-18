package com.iase24.test.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Данные для обновления содержимого комментария")
public class UpdateCommentRequest {

    @Schema(description = "ID комментария, который необходимо обновить", example = "1")
    private Long commentId;

    @Schema(description = "Новое содержимое комментария", example = "Обновленный комментарий.")
    private String newContent;
}
