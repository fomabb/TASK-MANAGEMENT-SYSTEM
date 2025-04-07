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
import org.fomabb.taskmanagement.dto.CommentDataDto;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.TrackTimeDatDto;
import org.fomabb.taskmanagement.dto.exception.CommonExceptionResponse;
import org.fomabb.taskmanagement.dto.response.TrackTimeResponse;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.service.TaskService;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

/**
 * Контроллер для управления задачами.
 * Обрабатывает запросы, связанные с задачами, такие как создание, обновление, получение и удаление задач.
 */
@RestController
@RequestMapping("/api/v1/tasks")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Управление задачами", description = "`Интерфейс для управления задачами`")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

//================================================SECTION~MANAGEMENT~Task=============================================//

    @Operation(
            summary = "Отслеживание рабочего времени",
            description = """
                    `
                    Обновляет рабочее время для указанного пользователя.
                    Принимает данные для отслеживания рабочего времени и возвращает информацию о работе.
                    Возвращает статус 201 Created при успешном отслеживании.
                    `
                    """,
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = TrackTimeDatDto.class))
            ),
            responses = {
                    @ApiResponse(responseCode = "201", description = "`Рабочее время успешно отслежено`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TrackTimeDatDto.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "403", description = "`Доступ запрещен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @PutMapping("/users/track-time")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<TrackTimeDatDto> trackTimeWorks(TrackTimeDatDto request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.trackTimeWorks(request));
    }

    @Operation(
            summary = "Получить трекер рабочего времени пользователя",
            description = """
                    `
                    Возвращает трекер рабочего времени для указанного пользователя.
                    Принимает ID пользователя и дату начала отслеживания.
                    Возвращает статус 200 OK при успешном получении данных.
                    `
                    """,
            parameters = {
                    @Parameter(name = "userId", description = "`Идентификатор пользователя`",
                            required = true, example = "2"),
                    @Parameter(name = "startDate", description = "`Дата, с которой начинается неделя (понедельник)`",
                            required = true, example = "2025-04-12")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Данные успешно получены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TrackTimeResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Некорректный запрос`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
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
    @GetMapping("/user/{userId}/show-time-tracking-board")
    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
    public ResponseEntity<Map<String, List<TrackTimeResponse>>> getTrackingBordByUserId(
            @PathVariable("userId") Long userId,
            @RequestParam("startDate") LocalDate startDate
    ) {
        return ResponseEntity.ok(taskService.getTrackingBordByUserId(userId, startDate));
    }

    @Operation(
            summary = "Получить задачи за неделю",
            description = """
                    `
                    Возвращает список задач, сгруппированных по дням недели. Принимает дату, и определяет задачи,
                    созданные с понедельника по воскресенье той недели.
                    `
                    """,
            parameters = {
                    @Parameter(name = "startDate", description = "`Дата, с которой начинается неделя (понедельник)`",
                            required = true, example = "2025-04-12")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Список задач за неделю успешно получен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDataDto.class))),
                    @ApiResponse(responseCode = "400", description = "`Неверный формат даты`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class)))
            }
    )
    @GetMapping("/by-weekday")
    public ResponseEntity<Map<String, List<TaskDataDto>>> getTasksByWeekday(@RequestParam LocalDate startDate) {
        return ResponseEntity.ok(taskService.getTasksByWeekday(startDate));
    }

    @Operation(
            summary = "Получить все задачи.",
            description = """
                    `
                    Возвращает список всех задач в системе.
                    Используйте этот метод для получения полной информации о всех доступных задачах.
                    `
                    """,
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Задачи успешно найдены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDataDto.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<PageableResponse<TaskDataDto>> getAllTask(
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(taskService.getAllTasks(PageRequest.of(page - 1, size)));
    }

    @Operation(
            summary = "Найти задачу по ID.",
            description = """
                    `
                    Возвращает задачу по указанному ID.  Используйте этот метод для получения подробной информации
                    о конкретной задаче.
                    `
                    """,
            parameters = {
                    @Parameter(name = "taskId", description = "`ID задачи, которую необходимо получить`",
                            required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Задача успешно найдена`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = TaskDataDto.class))
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
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDataDto> getTaskById(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }

    @Operation(
            summary = "Получить задачи по ID автора.",
            description = """
                    `
                    Этот метод позволяет извлечь пагинированный список задач, созданных автором с указанным ID.
                    Используйте его для получения всех задач, связанных с конкретным автором.
                    Укажите номер страницы и размер страницы для управления результатами.
                    `
                    """,
            parameters = {
                    @Parameter(name = "authorId", description = "`ID автора, чьи задачи необходимо получить`",
                            required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Успешно получены задачи`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Автор не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Неверный номер страницы или размер`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/author/{authorId}")
    public ResponseEntity<PageableResponse<TaskDataDto>> getTaskByAuthorId(
            @PathVariable("authorId") Long authorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(taskService.getTaskByAuthorId(authorId, PageRequest.of(page - 1, size)));
    }

    @Operation(
            summary = "Получить задачи по ID исполнителя.",
            description = """
                    `
                    Этот метод позволяет извлечь пагинированный список задач, назначенных конкретному исполнителю с указанным ID.
                    Используйте его для получения всех задач, связанных с конкретным исполнителем.
                    Укажите номер страницы и размер страницы для управления результатами.
                    `
                    """,
            parameters = {
                    @Parameter(name = "assigneeId", description = "`ID исполнителя, чьи задачи необходимо получить`",
                            required = true),
                    @Parameter(name = "page", description = "`Номер страницы для извлечения (по умолчанию 1)`",
                            example = "1"),
                    @Parameter(name = "size", description = "`Количество задач на странице (по умолчанию 10)`",
                            example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Успешно получены задачи`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Исполнитель не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Неверный номер страницы или размер`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/assignee/{assigneeId}")
    public ResponseEntity<PageableResponse<TaskDataDto>> getTaskByAssigneeId(
            @PathVariable("assigneeId") Long authorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(taskService.getTaskByAssigneeId(authorId, PageRequest.of(page - 1, size)));
    }

//================================================SECTION~COMMENT=====================================================//

    @Operation(
            summary = "Получить комментарии по ID автора.",
            description = """
                    `
                    Этот метод позволяет извлечь пагинированный список комментариев, созданных автором с указанным ID.
                    Используйте его для получения всех комментариев, связанных с конкретным автором.
                    Укажите номер страницы и размер страницы для управления результатами.
                    `
                    """,
            parameters = {
                    @Parameter(name = "authorId", description = "`ID автора, чьи комментарии необходимо получить`",
                            required = true),
                    @Parameter(name = "page", description = "`Номер страницы для извлечения (по умолчанию 1)`",
                            example = "1"),
                    @Parameter(name = "size", description = "`Количество комментариев на странице (по умолчанию 10)`",
                            example = "10")
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Успешно получены комментарии`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class))
                    ),
                    @ApiResponse(responseCode = "404", description = "`Автор не найден`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "400", description = "`Неверный номер страницы или размер`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    ),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping("/comments/author/{authorId}")
    public ResponseEntity<PageableResponse<CommentDataDto>> getCommentsByAuthorId(
            @PathVariable("authorId") Long authorId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(commentService.getCommentsByAuthorId(authorId, PageRequest.of(page - 1, size)));
    }

    @Operation(
            summary = "Получить комментарии по ID задачи.",
            description = """
                    `
                    Возвращает список комментариев для указанной задачи по её ID.
                    Используйте этот метод для получения всех комментариев, связанных с конкретной задачей.
                    `
                    """,
            parameters = {
                    @Parameter(name = "taskId", description = "`ID задачи, для которой необходимо получить комментарии`",
                            required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Комментарии успешно найдены`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = PageableResponse.class))
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
    @GetMapping("/comments/by-taskId/{taskId}")
    public ResponseEntity<PageableResponse<CommentDataDto>> getCommentsById(
            @PathVariable("taskId") Long taskId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(commentService.getCommentsById(taskId, PageRequest.of(page - 1, size)));
    }
}
