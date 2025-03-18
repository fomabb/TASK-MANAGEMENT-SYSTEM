package org.fomabb.taskmanagement.facade;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.exceptionhandler.exeption.DuplicateTitleException;
import org.fomabb.taskmanagement.service.TaskService;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class TaskFacade {

    private final TaskService taskService;

    public void removeTaskById(Long taskId) {
        TaskDataDto taskById = taskService.getTaskById(taskId);
        if (taskById != null) {
            taskService.removeTaskById(taskById.getId());
        } else {
            throw new EntityNotFoundException(String.format("Entity with ID %s not found", taskId));
        }
    }

    public CreatedTaskResponse createTask(CreateTaskRequest request) {
        if (!taskService.existsByTitle(request.getTitle())) {
            return taskService.createTask(request);
        } else {
            throw new DuplicateTitleException("A task with that name already exists.");
        }
    }
}
