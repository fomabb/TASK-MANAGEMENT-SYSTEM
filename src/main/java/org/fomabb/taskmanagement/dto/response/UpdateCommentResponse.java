package org.fomabb.taskmanagement.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
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

    @Schema(description = "Дата и время обновления комментария", example = "19-03-2025 19:55")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime updateAt;
}