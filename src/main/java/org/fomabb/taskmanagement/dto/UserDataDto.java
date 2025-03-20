package org.fomabb.taskmanagement.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;
import org.fomabb.taskmanagement.security.entity.enumeration.Role;

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

    @Schema(description = "Дата и время создания пользователя", example = "19-03-2025 19:38")
    @JsonFormat(pattern = "dd-MM-yyyy HH:mm")
    private LocalDateTime createdAt;

    @Schema(description = "Роль пользователя", example = "ROLE_USER")
    private Role userRole;
}
