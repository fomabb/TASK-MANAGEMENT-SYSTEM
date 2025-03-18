package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.mapper.CommentMapper;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.CommentRepository;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.TaskService;
import org.fomabb.taskmanagement.util.ConstantProject;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.util.ConstantProject.COMMENT_WITH_ID_S_NOT_FOUND;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final CommentMapper commentMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final CommentRepository commentRepository;

//==================================SECTION~ADMIN=======================================================================

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest requestBody) {
        Optional<User> user = userRepository.findById(requestBody.getAuthorId());

        if (user.isPresent()) {
            return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.createRequestToEntity(requestBody)));
        }
        throw new EntityNotFoundException(String.format(
                ConstantProject.USER_WITH_ID_S_NOT_FOUND, requestBody.getAuthorId()));
    }

    @Override
    public List<TaskDataDto> getAllTasks() {
        return taskMapper.listEntityToListDto(taskRepository.findAll());
    }

    @Override
    @Transactional
    public UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto requestBody) {
        Task existingTask = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId()))
                );
        Task updatedTask = taskMapper.updateDtoToEntity(requestBody);
        return taskMapper.entityToUpdateDto(taskRepository
                .save(taskMapper.buildUpdateTaskForSave(existingTask, updatedTask)));
    }

    @Override
    public TaskDataDto getTaskById(Long id) {
        return taskMapper.entityTaskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, id))));
    }

    @Override
    @Transactional
    public void removeTaskById(Long id) {
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional
    public void assignTaskPerformers(AssigneeTaskForUserRequest requestBody) {
        Task existingTask = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId()))
                );
        userRepository.findById(requestBody.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        ConstantProject.USER_WITH_ID_S_NOT_FOUND, requestBody.getAssigneeId()))
                );
        taskRepository.save(taskMapper.buildAssigneeToSave(existingTask, taskMapper.assigneeDtoToEntity(requestBody)));
    }

    @Override
    public boolean existsByTitle(String title) {
        return taskRepository.existsByTitle(title);
    }

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
        return commentMapper.entityCommentToCommentDto(commentRepository.save(
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

//==================================SECTION~USER========================================================================

    @Override
    @Transactional
    public UpdateTaskDataDto updateTaskForUser(UpdateTaskForUserDataRequest requestBody) {
        Task taskToSave = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.TASK_WITH_ID_S_NOT_FOUND, requestBody.getTaskId())
                ));

        User user = userRepository.findById(requestBody.getUserId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(ConstantProject.USER_WITH_ID_S_NOT_FOUND, requestBody.getUserId())
                ));
        Long currentUserId = getCurrentUserId();

        if (Objects.equals(user.getId(), currentUserId)) {
            taskToSave.setStatus(requestBody.getTaskStatus());
            taskToSave.setUpdatedAt(now());
        }
        return taskMapper.entityToUpdateDto(taskRepository.save(taskToSave));
    }

    /**
     * Проверка токена на валидность
     *
     * @return true, если токен валиден
     */
    private Long getCurrentUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (Long) authentication.getPrincipal(); // user_id из JWT
    }
}
