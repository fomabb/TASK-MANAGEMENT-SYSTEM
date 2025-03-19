package org.fomabb.taskmanagement.mapper.impl;

import org.fomabb.taskmanagement.dto.CommentsDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.mapper.CommentMapper;
import org.fomabb.taskmanagement.security.entity.User;
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
    public CommentAddedResponse entityCommentToCommentAddedDto(Comment comment) {
        return CommentAddedResponse.builder()
                .commentId(comment.getId())
                .taskId(comment.getTask().getId())
                .content(comment.getContent())
                .authorFirstName(comment.getAuthor().getFirstName())
                .authorLastName(comment.getAuthor().getLastName())
                .updateComment(comment.getUpdateAt())
                .build();
    }

    @Override
    public CommentsDataDto entityCommentToCommentDto(Comment comment) {
        return CommentsDataDto.builder()
                .id(comment.getId())
                .text(comment.getContent())
                .author(UserAuthorDataDto.builder().authorId(comment.getId()).build())
                .timeCreated(comment.getCreatedAt())
                .timeUpdated(comment.getUpdateAt())
                .build();
    }
}
