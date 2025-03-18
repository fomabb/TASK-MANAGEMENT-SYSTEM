package com.iase24.test.controller;

import com.iase24.test.dto.UpdateTaskDataDto;
import com.iase24.test.dto.request.AssigneeTaskForUserRequest;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.facade.TaskFacade;
import com.iase24.test.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
            summary = "Создание новой задачи.",
            description = "`В тело запроса необходимо добавить название, описание и приоритет.`"
    )
    @PostMapping
    public ResponseEntity<CreatedTaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @Operation(
            summary = "Редактирование таски.",
            description = "`В теле запроса необходимо прописать ID таски для ее редактирования.`"
    )
    @PatchMapping
    public ResponseEntity<UpdateTaskDataDto> updateTask(UpdateTaskDataDto request) {
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(taskService.updateTask(request));
    }

    @Operation(
            summary = "Установить исполнителя на задачу",
            description = """
                    `В тело запроса добавить ID задачи и ID пользователя, которого нужно сделать исполнителем.`
                    """
    )
    @PatchMapping("/assignee-by-taskId")
    public ResponseEntity<Void> assignTaskPerformersByIdTask(@RequestBody AssigneeTaskForUserRequest request) {
        taskService.assignTaskPerformers(request);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }

    @Operation(
            summary = "Удаление таски по ID.",
            description = "`Необходимо в путь прописать ID таски для ее удаления.`"
    )
    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> removeTaskById(@PathVariable("taskId") Long taskId) {
        taskFacade.removeTaskById(taskId);
        return ResponseEntity.noContent().build();
    }
}