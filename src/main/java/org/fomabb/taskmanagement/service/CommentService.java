package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.CommentsDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.PaginCommentsResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface CommentService {

    CommentAddedResponse addCommentToTaskById(CommentAddToTaskDataDtoRequest requestBody);

    UpdateCommentResponse updateComment(UpdateCommentRequest requestBody);

    PaginCommentsResponse getCommentsById(Long taskId, Pageable pageable);
}
