package org.fomabb.taskmanagement.service.impl;

import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.mapper.CommentMapper;
import org.fomabb.taskmanagement.repository.CommentRepository;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.entity.enumeration.Role;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.security.service.UserServiceSecurity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateAddCommentRequest;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateCommentAddedResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserEntityIdTwo;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {


    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserServiceSecurity userServiceSecurity;

    @InjectMocks
    private CommentServiceImpl commentService;

    @Test
    void addCommentToTaskById_ShouldReturnCommentAddedResponse() {
        // Arrange
        User userExist = generateUserEntityIdTwo();

        Task taskExist = generateTaskEntity();

        Comment comment = new Comment();

        CommentAddedResponse commentAddedResponse = generateCommentAddedResponse(userExist);

        CommentAddToTaskDataDtoRequest commentAddToTaskDataDtoRequest = generateAddCommentRequest(userExist);


        when(userRepository.findById(commentAddToTaskDataDtoRequest.getAuthorCommentId())).thenReturn(Optional.of(userExist));
        when(taskRepository.findById(commentAddToTaskDataDtoRequest.getTaskId())).thenReturn(Optional.of(taskExist));
        when(userServiceSecurity.getCurrentUser()).thenReturn(userExist);
        when(commentMapper.commentDtoToCommentEntity(userExist, taskExist, commentAddToTaskDataDtoRequest)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.entityCommentToCommentAddedDto(comment)).thenReturn(commentAddedResponse);

        // Act
        CommentAddedResponse response = commentService.addCommentToTaskById(commentAddToTaskDataDtoRequest);

        // Assert
        assertNotNull(response);
        verify(commentRepository, times(1)).save(comment);
    }

    @Test
    void updateComment_ShouldReturnUpdateCommentResponse() {

        // Arrange
        Long commentId = 1L;
        UpdateCommentRequest requestBody = UpdateCommentRequest.builder()
                .commentId(commentId)
                .newContent("Update content now()")
                .build();

        User currentUser = new User();
        currentUser.setId(2L);
        currentUser.setRole(Role.ROLE_USER);

        Comment existingComment = new Comment();
        existingComment.setId(commentId);
        existingComment.setContent("Old comment");

        User commentAuthor = new User();
        commentAuthor.setId(currentUser.getId());
        existingComment.setAuthor(commentAuthor);

        UpdateCommentResponse expectedResponse = UpdateCommentResponse.builder()
                .commentId(requestBody.getCommentId())
                .content(requestBody.getNewContent())
                .updateAt(now())
                .build();

        when(commentRepository.findById(commentId)).thenReturn(Optional.of(existingComment));
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);
        when(commentRepository.save(existingComment)).thenReturn(existingComment);

        // Act
        UpdateCommentResponse response = commentService.updateComment(requestBody);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getCommentId(), response.getCommentId());
        assertEquals(expectedResponse.getContent(), response.getContent());
        verify(commentRepository, times(1)).findById(commentId);
        verify(commentRepository, times(1)).save(existingComment);
    }
}
