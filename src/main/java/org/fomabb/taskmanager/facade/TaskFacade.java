package org.fomabb.taskmanager.facade;

import org.fomabb.taskmanager.dto.TaskDataDto;
import org.fomabb.taskmanager.dto.request.CreateTaskRequest;
import org.fomabb.taskmanager.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanager.exceptionhandler.exeption.DuplicateTitleException;
import org.fomabb.taskmanager.service.TaskService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
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
