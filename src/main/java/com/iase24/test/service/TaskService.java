package com.iase24.test.service;

import com.iase24.test.dto.TaskDataDto;
import com.iase24.test.dto.UpdateTaskDataDto;
import com.iase24.test.dto.request.AssigneeTaskForUserRequest;
import com.iase24.test.dto.request.CommentAddToTaskDataDtoRequest;
import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.request.UpdateCommentRequest;
import com.iase24.test.dto.response.CommentAddedResponse;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.dto.response.UpdateCommentResponse;

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