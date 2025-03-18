package org.fomabb.taskmanager.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@Builder
@Schema(description = "Ответ на обновление содержимого комментария")
public class UpdateCommentResponse {

    @Schema(description = "Обновленное содержимое комментария", example = "Обновленный комментарий.")
    private String content;

    @Schema(description = "Дата и время обновления комментария", example = "2023-03-18T12:34:56")
    private LocalDateTime updateAt;
}