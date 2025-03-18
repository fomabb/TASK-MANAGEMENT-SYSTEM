package org.fomabb.taskmanager.mapper;

import org.fomabb.taskmanager.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanager.dto.response.CommentAddedResponse;
import org.fomabb.taskmanager.entity.Comment;
import org.fomabb.taskmanager.entity.Task;
import org.fomabb.taskmanager.security.entity.User;

public interface CommentMapper {

    Comment commentDtoToCommentEntity(User userExist, Task taskExist, CommentAddToTaskDataDtoRequest dto);

    CommentAddedResponse entityCommentToCommentDto(Comment comment);
}