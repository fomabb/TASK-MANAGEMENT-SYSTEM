package org.fomabb.taskmanagement.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "Данные пользователя")
public class UserDataDto {

    @Schema(description = "Уникальный идентификатор пользователя", example = "1")
    private Long userId;

    @Schema(description = "Имя пользователя", example = "Иван")
    private String firstName;

    @Schema(description = "Фамилия пользователя", example = "Иванов")
    private String lastName;

    @Schema(description = "Дата и время создания пользователя", example = "2023-03-18T12:00:00")
    private LocalDateTime createdAt;
}
