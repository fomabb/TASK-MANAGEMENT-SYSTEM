package com.iase24.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

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
}
