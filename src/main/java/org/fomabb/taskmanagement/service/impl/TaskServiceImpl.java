package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.PageableTaskResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.TaskService;
import org.fomabb.taskmanagement.util.paging.PageableResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static org.fomabb.taskmanagement.util.ConstantProject.TASK_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.USER_WITH_ID_S_NOT_FOUND_CONST;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

//==================================SECTION~ADMIN=======================================================================

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest requestBody) {
        Optional<User> user = userRepository.findById(requestBody.getAuthorId());

        if (user.isPresent()) {
            return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.createRequestToEntity(requestBody)));
        }
        throw new EntityNotFoundException(String.format(USER_WITH_ID_S_NOT_FOUND_CONST, requestBody.getAuthorId()));
    }

    @Override
    public PageableTaskResponse getAllTasks(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(taskPage.getContent());
        return PageableResponseUtil.buildPageableResponse(taskDataDtos, taskPage, new PageableTaskResponse());
    }

    @Override
    @Transactional
    public UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto requestBody) {
        Task existingTask = taskRepository.findById(requestBody.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(TASK_WITH_ID_S_NOT_FOUND_CONST, requestBody.getTaskId()))
                );
        Task updatedTask = taskMapper.updateDtoToEntity(requestBody);
        return taskMapper.entityToUpdateDto(taskRepository
                .save(taskMapper.buildUpdateTaskForSave(existingTask, updatedTask)));
    }

    @Override
    public TaskDataDto getTaskById(Long id) {
        return taskMapper.entityTaskToTaskDto(taskRepository.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException(String.format(TASK_WITH_ID_S_NOT_FOUND_CONST, id))));
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
                        String.format(TASK_WITH_ID_S_NOT_FOUND_CONST, requestBody.getTaskId()))
                );
        userRepository.findById(requestBody.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        USER_WITH_ID_S_NOT_FOUND_CONST, requestBody.getAssigneeId()))
                );
        taskRepository.save(taskMapper.buildAssigneeToSave(existingTask, taskMapper.assigneeDtoToEntity(requestBody)));
    }

    @Override
    public boolean existsByTitle(String title) {
        return taskRepository.existsByTitle(title);
    }

    @Override
    public PageableTaskResponse getTaskByAuthorId(Long authorId, Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAllByAuthorId(authorId, pageable);
        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(taskPage.getContent());

        return PageableResponseUtil.buildPageableResponse(taskDataDtos, taskPage, new PageableTaskResponse());
    }

    @Override
    public PageableTaskResponse getTaskByAssigneeId(Long assigneeId, Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAllByAssigneeId(assigneeId, pageable);
        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(taskPage.getContent());

        return PageableResponseUtil.buildPageableResponse(taskDataDtos, taskPage, new PageableTaskResponse());
    }
}
