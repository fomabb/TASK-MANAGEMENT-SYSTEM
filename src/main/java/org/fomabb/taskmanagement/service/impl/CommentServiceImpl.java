package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanagement.dto.CommentsDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.PaginCommentsResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.mapper.CommentMapper;
import org.fomabb.taskmanagement.repository.CommentRepository;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.util.ConstantProject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.util.ConstantProject.COMMENT_WITH_ID_S_NOT_FOUND;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    @Transactional
    public CommentAddedResponse addCommentToTaskById(CommentAddToTaskDataDtoRequest requestBody) {
        User userExist = userRepository.findById(requestBody.getAuthorId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.USER_WITH_ID_S_NOT_FOUND, requestBody.getAuthorId())
                ));
        Task taskExist = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId())
                ));
        return commentMapper.entityCommentToCommentAddedDto(commentRepository.save(
                commentMapper.commentDtoToCommentEntity(userExist, taskExist, requestBody))
        );
    }

    @Override
    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentRequest requestBody) {
        Comment comment = commentRepository.findById(requestBody.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(COMMENT_WITH_ID_S_NOT_FOUND, requestBody.getCommentId())
                ));
        comment.setContent(requestBody.getNewContent());
        comment.setUpdateAt(now());
        commentRepository.save(comment);
        return new UpdateCommentResponse(comment.getContent(), comment.getUpdateAt());
    }

    @Override
    public PaginCommentsResponse getCommentsById(Long taskId, Pageable pageable) {
        Slice<Comment> commentsSlice = commentRepository.findCommentsByTaskId(taskId, pageable);
        List<CommentsDataDto> commentsDataDtos = commentsSlice.getContent()
                .stream()
                .map(commentMapper::entityCommentToCommentDto)
                .toList();
        return commentMapper.buildPagingCommentResponse(commentsDataDtos, commentsSlice);
    }
}
