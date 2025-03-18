package com.iase24.test.mapper.impl;

import com.iase24.test.dto.request.CommentAddToTaskDataDtoRequest;
import com.iase24.test.dto.response.CommentAddedResponse;
import com.iase24.test.entity.Comment;
import com.iase24.test.entity.Task;
import com.iase24.test.mapper.CommentMapper;
import com.iase24.test.security.entity.User;
import org.springframework.stereotype.Component;

import static java.time.LocalDateTime.now;

@Component
public class CommentMapperImpl implements CommentMapper {
    @Override
    public Comment commentDtoToCommentEntity(User userExist, Task taskExist, CommentAddToTaskDataDtoRequest dto) {
        return Comment.builder()
                .content(dto.getContent())
                .task(taskExist)
                .author(userExist)
                .createdAt(now())
                .build();
    }


    @Override
    public CommentAddedResponse entityCommentToCommentDto(Comment comment) {
        return CommentAddedResponse.builder()
                .commentId(comment.getId())
                .taskId(comment.getTask().getId())
                .content(comment.getContent())
                .authorFirstName(comment.getAuthor().getFirstName())
                .authorLastName(comment.getAuthor().getLastName())
                .updateComment(comment.getUpdateAt())
                .build();
    }
}
