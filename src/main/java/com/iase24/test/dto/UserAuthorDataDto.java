package com.iase24.test.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "Объект передачи данных идентификатора автора задачи")
public class UserAuthorDataDto {

    @Schema(description = "Идентификатор автора", example = "1")
    private Long authorId;
}
