package org.fomabb.taskmanager.mapper.impl;

import org.fomabb.taskmanager.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanager.dto.response.CommentAddedResponse;
import org.fomabb.taskmanager.entity.Comment;
import org.fomabb.taskmanager.entity.Task;
import org.fomabb.taskmanager.mapper.CommentMapper;
import org.fomabb.taskmanager.security.entity.User;
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
