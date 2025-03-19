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
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.exception.CommonExceptionResponse;
import org.fomabb.taskmanagement.dto.response.PaginCommentsResponse;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.service.TaskService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Таск трекер задач", description = "API для управления задачами зарегистрированных пользователей")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;
    private final CommentService commentService;

//================================================SECTION~MANAGEMENT~Task=============================================//

    @Operation(
            summary = "Показать все задачи.",
            description = "`Выводит все задачи, которые имеются в базе данных.`",
            responses = {
                    @ApiResponse(responseCode = "200", description = "`Список задач успешно получен`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = List.class))
                    ),
                    @ApiResponse(responseCode = "204", description = "`Нет задач для отображения`"),
                    @ApiResponse(responseCode = "500", description = "`Ошибка сервера`",
                            content = @Content(mediaType = "application/json",
                                    schema = @Schema(implementation = CommonExceptionResponse.class))
                    )
            }
    )
    @GetMapping
    public ResponseEntity<List<TaskDataDto>> getAllTask() {
        return ResponseEntity.ok(taskService.getAllTasks());
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

//================================================SECTION~COMMENT=====================================================//

//    @GetMapping("/comments-by-taskId/{taskId}")
//    public ResponseEntity<Slice<CommentsDataDto>> getCommentsById(
//            @PathVariable("taskId") Long taskId,
//            @RequestParam(defaultValue = "1") int page,
//            @RequestParam(defaultValue = "10") int size
//    ) {
//        return ResponseEntity.ok(commentService.getCommentsById(taskId, PageRequest.of(page - 1, size)));
//    }

    @GetMapping("/comments-by-taskId/{taskId}")
    public ResponseEntity<PaginCommentsResponse> getCommentsById(
            @PathVariable("taskId") Long taskId,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(commentService.getCommentsById(taskId, PageRequest.of(page - 1, size)));
    }
}
