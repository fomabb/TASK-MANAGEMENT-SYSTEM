package com.iase24.test.controller;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.service.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/task")
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Управление задачами", description = "API для управления задачами")
public class TaskController {

    private final TaskService taskService;

    @Operation(
            summary = "Создание новой задачи.",
            description = "`В тело запроса необходимо добавить название, описание и приоритет.`"
    )
    @PostMapping
    public ResponseEntity<CreatedTaskResponse> createTask(@RequestBody CreateTaskRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(taskService.createTask(request));
    }

    @Operation(
            summary = "Показать все задачи.",
            description = "`Выводит все задачи, которые имеются в базе данных.`"
    )
    @GetMapping
    public ResponseEntity<List<TaskDataDto>> getAllTask() {
        return ResponseEntity.ok(taskService.getAllTask());
    }
}
