package org.fomabb.taskmanager.controller;

import org.fomabb.taskmanager.dto.UpdateTaskDataDto;
import org.fomabb.taskmanager.dto.exception.CommonExceptionResponse;
import org.fomabb.taskmanager.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanager.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanager.dto.request.CreateTaskRequest;
import org.fomabb.taskmanager.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanager.dto.response.CommentAddedResponse;
import org.fomabb.taskmanager.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanager.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanager.facade.TaskFacade;
import org.fomabb.taskmanager.service.TaskService;
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
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin")
@Slf4j
@Tag(name = "Администраторский API", description = "Интерфейс для управления админкой")
@SecurityRequirement(name = "bearerAuth")
@RequiredArgsConstructor
public class AdminController {

    private final TaskService taskService;
    private final TaskFacade taskFacade;

    @Operation(
            summary = "Создание новой задачи",
            description = """
                    `
                    Создает новую задачу. В теле запроса необходимо указать название, описание и приоритет задачи.
                    Если приоритет не указан, будет применено значение по умолчанию.
                    `
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Задача создана`",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CreatedTaskResponse.class))
                            }),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            })
    @PostMapping
    public ResponseEntity<CreatedTaskResponse> createTask(@Valid @RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskFacade.createTask(request));
    }

    @Operation(
            summary = "Редактирование задачи",
            description = """
                    `
                    Обновляет существующую задачу. В теле запроса необходимо указать ID задачи и поля, которые вы хотите
                    обновить. Например, можно изменить только заголовок или описание задачи. Если какое-либо из полей не
                    указано, оно останется неизменным.
                    `
                    """,
            parameters = {
                    @Parameter(
                            name = "request",
                            description = """
                                    `DTO с данными для обновления задачи. Содержит ID задачи и обновляемые поля.`
                                    """,
                            required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "202", description = "`Задача успешно обновлена`",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = UpdateTaskDataDto.class))
                            }),
                    @ApiResponse(responseCode = "400", description = """
                            `Неверный запрос. Проверьте, что ID задачи указан и поля соответствуют требованиям.`
                            """,
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            }),
                    @ApiResponse(responseCode = "404", description = "`Задача не найдена по указанному ID`",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })
            }
    )
    @PatchMapping
    public ResponseEntity<UpdateTaskDataDto> updateTask(UpdateTaskDataDto request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateTask(request));
    }

    @Operation(
            summary = "Установить исполнителя на задачу",
            description = """
                    `В тело запроса добавить ID задачи и ID пользователя, которого нужно сделать исполнителем.`
                    """,
            responses = {
                    @ApiResponse(
                            responseCode = "202",
                            description = "Исполнитель успешно назначен"

                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = """
                                   `Неверный запрос. Проверьте, что ID задачи и ID пользователя указаны корректно.`
                                   """,
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            }
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "`Задача или пользователь не найдены`",
                            content = {@Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                            })

            }
    )
    @PatchMapping("/assignee-by-taskId")
    public ResponseEntity<Void> assignTaskPerformersByIdTask(@RequestBody AssigneeTaskForUserRequest request) {
        taskService.assignTaskPerformers(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Удаление таски по ID.",
            description = "`Необходимо в путь прописать ID таски для ее удаления.`",
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
            summary = "Добавление комментария к задаче",
            description = """
                        `
                        Добавляет комментарий к задаче по указанному ID.
                        В теле запроса необходимо указать данные комментария, включая текст и ID задачи.
                        `
                    """,
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Комментарий успешно добавлен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommentAddedResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "404", description = "`Задача не найдена`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class)
                            )
                    ),
                    @ApiResponse(responseCode = "400", description = "`Ошибка валидации данных комментария`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class)
                            )
                    )
            }
    )
    @PostMapping("/add-comment-to-task")
    public ResponseEntity<CommentAddedResponse> addCommentToTaskById(
            @RequestBody CommentAddToTaskDataDtoRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.addCommentToTaskById(request));
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
    @PutMapping("/update-comment")
    public ResponseEntity<UpdateCommentResponse> updateComment(@RequestBody UpdateCommentRequest request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateComment(request));
    }
}