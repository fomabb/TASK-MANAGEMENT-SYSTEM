package org.fomabb.taskmanagement.mapper;

import org.fomabb.taskmanagement.dto.CommentsDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.PaginCommentsResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.security.entity.User;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface CommentMapper {

    Comment commentDtoToCommentEntity(User userExist, Task taskExist, CommentAddToTaskDataDtoRequest dto);

    CommentAddedResponse entityCommentToCommentAddedDto(Comment comment);

    CommentsDataDto entityCommentToCommentDto(Comment comment);

    PaginCommentsResponse buildPagingCommentResponse(List<CommentsDataDto> commentsDataDtos, Slice<Comment> commentsSlice);
}