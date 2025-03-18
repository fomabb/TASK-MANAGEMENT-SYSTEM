package org.fomabb.taskmanager.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanager.dto.TaskDataDto;
import org.fomabb.taskmanager.dto.UpdateTaskDataDto;
import org.fomabb.taskmanager.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanager.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanager.dto.request.CreateTaskRequest;
import org.fomabb.taskmanager.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanager.dto.response.CommentAddedResponse;
import org.fomabb.taskmanager.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanager.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanager.entity.Comment;
import org.fomabb.taskmanager.entity.Task;
import org.fomabb.taskmanager.mapper.CommentMapper;
import org.fomabb.taskmanager.mapper.TaskMapper;
import org.fomabb.taskmanager.repository.CommentRepository;
import org.fomabb.taskmanager.repository.TaskRepository;
import org.fomabb.taskmanager.security.entity.User;
import org.fomabb.taskmanager.security.repository.UserRepository;
import org.fomabb.taskmanager.service.TaskService;
import org.fomabb.taskmanager.util.ConstantProject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanager.util.ConstantProject.COMMENT_WITH_ID_S_NOT_FOUND;

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
    public List<TaskDataDto> getAllTask() {
        return taskMapper.listEntityToListDto(taskRepository.findAll());
    }

    @Override
    @Transactional
    public UpdateTaskDataDto updateTask(UpdateTaskDataDto requestBody) {
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
}
