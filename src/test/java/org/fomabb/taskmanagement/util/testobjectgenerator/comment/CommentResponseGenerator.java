package org.fomabb.taskmanagement.util.testobjectgenerator.comment;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.CommentDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;

import java.util.List;

import static java.time.LocalDateTime.now;

@UtilityClass
public class CommentResponseGenerator {

    public static CommentAddedResponse generateCommentAddedResponse() {
        return CommentAddedResponse.builder()
                .commentId(1L)
                .taskId(1L)
                .content("Task content")
                .authorFirstName("Ivan")
                .authorLastName("Ivanov")
                .updateComment(null)
                .build();
    }


    public static UpdateCommentResponse generateUpdateCommentResponse(UpdateCommentRequest request) {
        return UpdateCommentResponse.builder()
                .content(request.getNewContent())
                .commentId(request.getCommentId())
                .updateAt(now())
                .build();
    }

    public static CommentAddedResponse generateCommentAddedResponse(User userExist) {
        return CommentAddedResponse.builder()
                .commentId(1L)
                .taskId(1L)
                .content("New Comment")
                .authorFirstName(userExist.getFirstName())
                .authorLastName(userExist.getLastName())
                .updateComment(now())
                .build();
    }

    public static CommentAddToTaskDataDtoRequest generateAddCommentRequest(User userExist) {
        return CommentAddToTaskDataDtoRequest.builder()
                .taskId(userExist.getId())
                .authorId(userExist.getId())
                .build();
    }

    public static List<CommentDataDto> generateCommentDataDto() {
        CommentDataDto comment1 = CommentDataDto.builder()
                .id(1L)
                .content("Content-1")
                .author(UserAuthorDataDto.builder().authorId(1L).build())
                .createAt(now())
                .build();

        CommentDataDto comment2 = CommentDataDto.builder()
                .id(2L)
                .content("Content-2")
                .author(UserAuthorDataDto.builder().authorId(1L).build())
                .createAt(now())
                .build();

        return List.of(comment1, comment2);
    }

    public static List<Comment> generateListComment() {
        Comment comment1 = Comment.builder()
                .id(1L)
                .content("Content-1")
                .task(Task.builder().id(1L).build())
                .author(User.builder().id(1L).build())
                .createdAt(now())
                .build();

        Comment comment2 = Comment.builder()
                .id(1L)
                .content("Content-2")
                .task(Task.builder().id(1L).build())
                .author(User.builder().id(1L).build())
                .createdAt(now())
                .build();

        return List.of(comment1, comment2);
    }

    public static PageableResponse<CommentDataDto> generatePageCommentResponse(List<CommentDataDto> commentDataDtos, List<Comment> commentList) {
        return PageableResponse.<CommentDataDto>builder()
                .content(commentDataDtos)
                .pageNumber(1) // Если сервис конвертирует 0-based → 1-based
                .pageSize(10)
                .isFirst(true)
                .isLast(true)
                .numberOfElements(commentList.size())
                .isEmpty(false)
                .totalPages(1)
                .totalItems(commentList.size())
                .build();
    }
}
