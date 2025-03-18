package com.iase24.test.facade;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.service.TaskService;
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
}
