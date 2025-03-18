package com.iase24.test.mapper;

import com.iase24.test.dto.request.CommentAddToTaskDataDtoRequest;
import com.iase24.test.dto.response.CommentAddedResponse;
import com.iase24.test.entity.Comment;
import com.iase24.test.entity.Task;
import com.iase24.test.security.entity.User;

public interface CommentMapper {

    Comment commentDtoToCommentEntity(User userExist, Task taskExist, CommentAddToTaskDataDtoRequest dto);

    CommentAddedResponse entityCommentToCommentDto(Comment comment);
}