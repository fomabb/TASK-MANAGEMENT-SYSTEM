package org.fomabb.taskmanagement.util.testobjectgenerator.comment;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.security.entity.User;

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
}
