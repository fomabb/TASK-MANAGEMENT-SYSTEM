package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanagement.dto.CommentsDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.PageableCommentsResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.exceptionhandler.exeption.BusinessException;
import org.fomabb.taskmanagement.mapper.CommentMapper;
import org.fomabb.taskmanagement.repository.CommentRepository;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.entity.enumeration.Role;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.util.paging.PageableResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.security.service.UserServiceSecurity.getCurrentUserId;
import static org.fomabb.taskmanagement.security.service.UserServiceSecurity.getCurrentUserRole;
import static org.fomabb.taskmanagement.util.ConstantProject.COMMENT_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.TASK_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.USER_WITH_ID_S_NOT_FOUND_CONST;

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
                        String.format(USER_WITH_ID_S_NOT_FOUND_CONST, requestBody.getAuthorId())
                ));
        Task taskExist = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(TASK_WITH_ID_S_NOT_FOUND_CONST, requestBody.getTaskId())
                ));
        boolean isAdmin = getCurrentUserRole().equals(Role.ROLE_ADMIN);

        if (taskExist.getAssignee().getId().equals(userExist.getId()) || isAdmin) {
            Comment comment = commentMapper.commentDtoToCommentEntity(userExist, taskExist, requestBody);
            return commentMapper.entityCommentToCommentAddedDto(commentRepository.save(comment));
        } else {
            throw new BusinessException("You don't have the rights to add a comment to this task");
        }
    }

    @Override
    public PageableCommentsResponse getCommentsById(Long taskId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findCommentsByTaskId(taskId, pageable);
        List<CommentsDataDto> commentsDataDtos = commentsPage.getContent()
                .stream()
                .map(commentMapper::entityCommentToCommentDto)
                .toList();

        return PageableResponseUtil.buildPageableResponse(commentsDataDtos, commentsPage, new PageableCommentsResponse());
    }

    @Override
    public PageableCommentsResponse getCommentsByAuthorId(Long authorId, Pageable pageable) {
        Page<Comment> pageComment = commentRepository.findAllByAuthorId(authorId, pageable);
        List<CommentsDataDto> commentsDataDtos = pageComment.getContent()
                .stream()
                .map(commentMapper::entityCommentToCommentDto)
                .toList();

        return PageableResponseUtil.buildPageableResponse(commentsDataDtos, pageComment, new PageableCommentsResponse());
    }

    @Override
    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentRequest requestBody) {
        Comment comment = commentRepository.findById(requestBody.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(COMMENT_WITH_ID_S_NOT_FOUND_CONST, requestBody.getCommentId())
                ));

        Long currentUserId = getCurrentUserId();
        boolean isAdmin = getCurrentUserRole().equals(Role.ROLE_ADMIN);

        if (Objects.equals(comment.getAuthor().getId(), currentUserId) || isAdmin) {
            comment.setContent(requestBody.getNewContent());
            comment.setUpdateAt(now());
            commentRepository.save(comment);
        } else {
            throw new BusinessException("У вас нет прав для обновления этого комментария");
        }

        return new UpdateCommentResponse(comment.getContent(), comment.getUpdateAt());
    }
}
