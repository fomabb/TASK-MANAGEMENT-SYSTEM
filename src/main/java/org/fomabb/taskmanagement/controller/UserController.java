package org.fomabb.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.dto.exception.CommonExceptionResponse;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Контроллер для управления пользователями.
 * Обрабатывает запросы, связанные с пользователями, такие как получение и обновление данных.
 */
@RestController
@RequestMapping("/api/v1/user")
@Slf4j
@Tag(name = "Управление пользователями", description = "`Интерфейс для управления пользователями`")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final CommentService commentService;

    @Operation(
            summary = "Получить информацию о пользователе",
            description = """
                        Возвращает данные о пользователе по указанному ID.
                        Используйте этот метод для получения подробной информации о конкретном пользователе.
                    """,
            parameters = {
                    @Parameter(name = "userId", description = "`ID пользователя, информацию о котором необходимо получить`",
                            required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Пользователь успешно найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UserDataDto.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Пользователь не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/{userId}")
    public ResponseEntity<UserDataDto> getUserById(@PathVariable("userId") Long userId) {
        return ResponseEntity.ok(userService.getUserById(userId));
    }

    @Operation(
            summary = "Получить список всех пользователей",
            description = """
                        Возвращает список всех пользователей в системе.
                        Используйте этот метод для получения информации о всех зарегистрированных пользователях.
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Список пользователей успешно получен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<UserDataDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUser());
    }

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
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(userService.updateTaskStatusForUser(request));
    }

    @Operation(
            summary = "Добавить комментарий к задаче по ID.",
            description = """
                    `
                    Добавляет комментарий к задаче по указанному ID.
                    Используйте этот метод для добавления комментариев к существующим задачам, проверяя, является
                    ли пользователь исполнителем.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для добавления комментария",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CommentAddToTaskDataDtoRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Комментарий успешно добавлен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentAddedResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Задача не найдена`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/comments/post")
    public ResponseEntity<CommentAddedResponse> addCommentToTaskById(@RequestBody CommentAddToTaskDataDtoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(commentService.addCommentToTaskById(request));
    }

    @Operation(
            summary = "Обновление содержимого комментария",
            description = "`Обновляет содержимое комментария по указанному ID.`",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    description = "`Данные для обновления комментария`",
                    content = @io.swagger.v3.oas.annotations.media.Content(
                            mediaType = "application/json",
                            schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = UpdateCommentRequest.class)
                    )
            ),
            responses = {
                    @ApiResponse(responseCode = "204", description = "`Комментарий успешно обновлен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateCommentResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Комментарий не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/comments/update")
    public ResponseEntity<UpdateCommentResponse> updateComment(@RequestBody UpdateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.updateComment(request));
    }
}
