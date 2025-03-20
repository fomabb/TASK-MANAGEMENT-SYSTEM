package org.fomabb.taskmanagement.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.exception.CommonExceptionResponse;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.facade.TaskFacade;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.service.TaskService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Контроллер для управления административными функциями.
 * Обрабатывает запросы, связанные с администрированием, такие как управление пользователями и настройками системы.
 */
@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
@Tag(name = "Управления административными функциями", description = "`Интерфейс для управления админкой`")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AdminController {

    private final TaskService taskService;
    private final TaskFacade taskFacade;
    private final CommentService commentService;

    @Operation(
            summary = "Создать новую задачу.",
            description = """
                    `
                    Создает новую задачу в системе.
                    Используйте этот метод для добавления новой задачи, указывая необходимые параметры.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для создания задачи",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateTaskRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Задача успешно создана`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreatedTaskResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PostMapping("/tasks/create-task")
    public ResponseEntity<CreatedTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskFacade.createTask(request));
    }

    @Operation(
            summary = "Обновить задачу.",
            description = """
                    `
                    Обновляет существующую задачу в системе.
                    Используйте этот метод для изменения параметров задачи, таких как:
                    - Название
                    - Описание
                    - Статус
                    - Приоритет
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для обновления задачи",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = UpdateTaskDataDto.class))
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
    @PatchMapping("/tasks/update")
    public ResponseEntity<UpdateTaskDataDto> updateTask(@RequestBody UpdateTaskDataDto request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateTaskForAdmin(request));
    }

    @Operation(
            summary = "Назначить исполнителей задачи по ID.",
            description = """
                    `
                    Назначает исполнителей для задачи по указанному ID.
                    Используйте этот метод для обновления исполнителей задачи.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Данные для назначения исполнителей",
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AssigneeTaskForUserRequest.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "202", description = "`Исполнители успешно назначены`",
                            content = @Content(mediaType = "application/json")
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
    @PatchMapping("/assignee-by-taskId")
    public ResponseEntity<Void> assignTaskPerformersByIdTask(@RequestBody AssigneeTaskForUserRequest request) {
        taskService.assignTaskPerformers(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Удаление задачи по ID.",
            description = "`Необходимо в путь прописать ID задачи для ее удаления.`",
            parameters = {
                    @Parameter(
                            name = "taskId",
                            description = "`ID задачи, которую необходимо удалить.`",
                            required = true,
                            example = "1"
                    )
            },
            responses = {
                    @ApiResponse(
                            responseCode = "204",
                            description = "`Задача успешно удалена`"
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "`Задача не найдена по указанному ID`"
                    )
            }
    )
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> removeTaskById(@PathVariable("taskId") Long taskId) {
        taskFacade.removeTaskById(taskId);
        return ResponseEntity.noContent().build();
    }

    @Operation(
            summary = "Добавить комментарий к задаче по ID.",
            description = """
                    `
                    Добавляет комментарий к задаче по указанному ID.
                    Используйте этот метод для добавления комментариев к существующим задачам.
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
    @PostMapping("/comments/post")
    public ResponseEntity<CommentAddedResponse> addCommentToTaskById(
            @RequestBody CommentAddToTaskDataDtoRequest request) {
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
    @PatchMapping("/comments/update")
    public ResponseEntity<UpdateCommentResponse> updateComment(@RequestBody UpdateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(commentService.updateComment(request));
    }
}