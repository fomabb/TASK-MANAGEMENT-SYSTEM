package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.PaginTaskResponse;
import org.springframework.data.domain.Pageable;

public interface TaskService {

    CreatedTaskResponse createTask(CreateTaskRequest requestBody);

    PaginTaskResponse getAllTasks(Pageable pageable);

    UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto requestBody);

    TaskDataDto getTaskById(Long id);

    void removeTaskById(Long id);

    void assignTaskPerformers(AssigneeTaskForUserRequest requestBody);

    boolean existsByTitle(String title);

    UpdateTaskDataDto updateTaskStatusForUser(UpdateTaskForUserDataRequest requestBody);
}