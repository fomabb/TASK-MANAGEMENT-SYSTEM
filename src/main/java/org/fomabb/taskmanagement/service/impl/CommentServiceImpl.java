package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.fomabb.taskmanagement.dto.CommentDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
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
import org.fomabb.taskmanagement.security.service.UserServiceSecurity;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;
import org.fomabb.taskmanagement.util.pagable.PageableResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.util.ConstantProject.COMMENT_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.TASK_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.USER_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.VALIDATION_REFERENCES_CONST;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentMapper commentMapper;
    private final CommentRepository commentRepository;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;
    private final PageableResponseUtil pageableResponseUtil;
    private final UserServiceSecurity userServiceSecurity;

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
        boolean isAdmin = userServiceSecurity.getCurrentUser().getRole().equals(Role.ROLE_ADMIN);
        Long currencyUserId = userServiceSecurity.getCurrentUser().getId();

        if (Objects.equals(userExist.getId(), taskExist.getAssignee().getId()) ||  isAdmin) {
            if (Objects.equals(userExist.getId(), currencyUserId)  ||  isAdmin) {
                Comment comment = commentMapper.commentDtoToCommentEntity(userExist, taskExist, requestBody);
                return commentMapper.entityCommentToCommentAddedDto(commentRepository.save(comment));
            } else {
                throw new BusinessException(VALIDATION_REFERENCES_CONST);
            }
        } else {
            throw new BusinessException(VALIDATION_REFERENCES_CONST);

        }
    }

    @Override
    public PageableResponse<CommentDataDto> getCommentsById(Long taskId, Pageable pageable) {
        Page<Comment> commentsPage = commentRepository.findCommentsByTaskId(taskId, pageable);
        List<CommentDataDto> commentDataDtos = commentMapper.listCommentEntityToListCommentDto(commentsPage.getContent());
        return pageableResponseUtil.buildPageableResponse(commentDataDtos, commentsPage, new PageableResponse<>());
    }

    @Override
    public PageableResponse<CommentDataDto> getCommentsByAuthorId(Long authorId, Pageable pageable) {
        Page<Comment> pageComment = commentRepository.findAllByAuthorId(authorId, pageable);
        List<CommentDataDto> commentDataDtos = commentMapper.listCommentEntityToListCommentDto(pageComment.getContent());
        return pageableResponseUtil.buildPageableResponse(commentDataDtos, pageComment, new PageableResponse<>());
    }

    @Override
    public void removeCommentById(Long commentId) {
        if (!commentRepository.existsById(commentId)) {
            throw new EntityNotFoundException(COMMENT_WITH_ID_S_NOT_FOUND_CONST);
        }
        commentRepository.deleteById(commentId);
    }

    @Override
    @Transactional
    public UpdateCommentResponse updateComment(UpdateCommentRequest requestBody) {
        Comment comment = commentRepository.findById(requestBody.getCommentId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(COMMENT_WITH_ID_S_NOT_FOUND_CONST, requestBody.getCommentId())
                ));


        Long currentUserId = userServiceSecurity.getCurrentUser().getId();
        boolean isAdmin = userServiceSecurity.getCurrentUser().getRole().equals(Role.ROLE_ADMIN);


        if (Objects.equals(comment.getAuthor().getId(), currentUserId) || isAdmin) {
            comment.setContent(requestBody.getNewContent());
            comment.setUpdateAt(now());
            commentRepository.save(comment);
        } else {
            throw new BusinessException(VALIDATION_REFERENCES_CONST);
        }

        return new UpdateCommentResponse(comment.getId(), comment.getContent(), comment.getUpdateAt());
    }
}
