package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;

import java.util.List;

public interface TaskService {

    CreatedTaskResponse createTask(CreateTaskRequest requestBody);

    List<TaskDataDto> getAllTasks();

    UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto requestBody);

    TaskDataDto getTaskById(Long id);

    void removeTaskById(Long id);

    void assignTaskPerformers(AssigneeTaskForUserRequest requestBody);

    boolean existsByTitle(String title);

    UpdateTaskDataDto updateTaskForUser(UpdateTaskForUserDataRequest requestBody);
}