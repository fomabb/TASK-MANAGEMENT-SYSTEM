package org.fomabb.taskmanagement.util.testobjectgenerator.comment;

import lombok.experimental.UtilityClass;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;

import java.time.LocalDateTime;

@UtilityClass
public class CommentResponseGenerator {

    public static CommentAddedResponse generateCommentAddedResponse() {
        return CommentAddedResponse.builder()
                .commentId(1L)
                .taskId(1L)
                .content("Task content")
                .authorFirstName("Ivan")
                .authorLastName("Ivanov")
                .updateComment(LocalDateTime.parse("20-03-2025 14:22"))
                .build();
    }

    public static UpdateCommentResponse generateUpdateCommentResponse() {
        return UpdateCommentResponse.builder()
                .content("Update comment")
                .updateAt(LocalDateTime.parse("2025-03-20T18:17:53.3362947"))
                .build();
    }
}
