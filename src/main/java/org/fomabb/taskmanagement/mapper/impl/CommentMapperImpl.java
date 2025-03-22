package org.fomabb.taskmanagement.mapper.impl;

import org.fomabb.taskmanagement.dto.CommentDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.mapper.CommentMapper;
import org.fomabb.taskmanagement.security.entity.User;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

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
    public CommentDataDto entityCommentToCommentDto(Comment comment) {
        return CommentDataDto.builder()
                .id(comment.getId())
                .content(comment.getContent())
                .author(
                        UserAuthorDataDto.builder().authorId(comment.getAuthor().getId()).build()
                )
                .createAt(comment.getCreatedAt())
                .timeUpdated(comment.getUpdateAt())
                .build();
    }

    @Override
    public List<CommentDataDto> listCommentEntityToListCommentDto(List<Comment> comments) {
        if (comments == null) {
            return Collections.emptyList();
        }
        return comments.stream().map(this::entityCommentToCommentDto).toList();
    }
}
