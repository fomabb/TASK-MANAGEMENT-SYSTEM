package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;

import java.util.List;

public interface TaskService {

    CreatedTaskResponse createTask(CreateTaskRequest requestBody);

    List<TaskDataDto> getAllTasks();

    UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto requestBody);

    TaskDataDto getTaskById(Long id);

    void removeTaskById(Long id);

    void assignTaskPerformers(AssigneeTaskForUserRequest requestBody);

    boolean existsByTitle(String title);

    CommentAddedResponse addCommentToTaskById(CommentAddToTaskDataDtoRequest requestBody);

    UpdateCommentResponse updateComment(UpdateCommentRequest requestBody);

    UpdateTaskDataDto updateTaskForUser(UpdateTaskForUserDataRequest requestBody);
}