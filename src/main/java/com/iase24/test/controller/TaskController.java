package com.iase24.test.controller;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Управление задачами", description = "API для управления задачами")
@SecurityRequirement(name = "bearerAuth")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "Показать все задачи.",
            description = "`Выводит все задачи, которые имеются в базе данных.`"
    )
    @GetMapping
    public ResponseEntity<List<TaskDataDto>> getAllTask() {
        return ResponseEntity.ok(taskService.getAllTask());
    }

    @Operation(
            summary = "Найти таску по ID.",
            description = "`В путь необходимо добавить ID таски.`"
    )
    @GetMapping("/{taskId}")
    public ResponseEntity<TaskDataDto> getTaskById(@PathVariable("taskId") Long taskId) {
        return ResponseEntity.ok(taskService.getTaskById(taskId));
    }
}
