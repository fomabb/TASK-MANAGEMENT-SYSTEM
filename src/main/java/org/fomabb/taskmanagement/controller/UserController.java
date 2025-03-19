package org.fomabb.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.exception.CommonExceptionResponse;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "Управление пользователями", description = "`Интерфейс для управления пользователями`")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final TaskService taskService;

    @Operation(
            summary = "Обновить задачу пользователя.",
            description = """
                    `
                    Обновляет существующую задачу для указанного пользователя.
                    Принимает данные для обновления, включая статус задачи и возможные комментарии.
                    Возвращает статус 202 Accepted при успешном обновлении.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateTaskForUserDataRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "`Задача успешно обновлена`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateTaskDataDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Задача или пользователь не найдены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PatchMapping("/tasks/update-status")
    public ResponseEntity<UpdateTaskDataDto> updateTaskStatus(@RequestBody UpdateTaskForUserDataRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateTaskStatusForUser(request));
    }
}
