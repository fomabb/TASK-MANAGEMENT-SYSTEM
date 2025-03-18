package org.fomabb.taskmanager.service;

import org.fomabb.taskmanager.dto.TaskDataDto;
import org.fomabb.taskmanager.dto.UpdateTaskDataDto;
import org.fomabb.taskmanager.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanager.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanager.dto.request.CreateTaskRequest;
import org.fomabb.taskmanager.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanager.dto.response.CommentAddedResponse;
import org.fomabb.taskmanager.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanager.dto.response.UpdateCommentResponse;

import java.util.List;

public interface TaskService {

    CreatedTaskResponse createTask(CreateTaskRequest requestBody);

    List<TaskDataDto> getAllTask();

    UpdateTaskDataDto updateTask(UpdateTaskDataDto requestBody);

    TaskDataDto getTaskById(Long id);

    void removeTaskById(Long id);

    void assignTaskPerformers(AssigneeTaskForUserRequest requestBody);

    boolean existsByTitle(String title);

    CommentAddedResponse addCommentToTaskById(CommentAddToTaskDataDtoRequest requestBody);

    UpdateCommentResponse updateComment(UpdateCommentRequest requestBody);
}