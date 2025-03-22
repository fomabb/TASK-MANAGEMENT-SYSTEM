package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.service.TaskService;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;
import org.fomabb.taskmanagement.util.pagable.PageableResponseUtil;
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
    private final PageableResponseUtil pageableResponseUtil;

//==================================SECTION~ADMIN=======================================================================

    @Override
    @Transactional
    public CreatedTaskResponse createTask(CreateTaskRequest dto) {
        Optional<User> user = userRepository.findById(dto.getAuthorId());

        if (user.isPresent()) {
            return taskMapper.entityToCreateResponse(taskRepository.save(taskMapper.createRequestToEntity(dto)));
        }
        throw new EntityNotFoundException(String.format(USER_WITH_ID_S_NOT_FOUND_CONST, dto.getAuthorId()));
    }

    @Override
    public PageableResponse<TaskDataDto> getAllTasks(Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAll(pageable);
        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(taskPage.getContent());

        return pageableResponseUtil.buildPageableResponse(taskDataDtos, taskPage, new PageableResponse<>());
    }

    @Override
    @Transactional
    public UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto dto) {
        Task existingTask = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(TASK_WITH_ID_S_NOT_FOUND_CONST, dto.getTaskId()))
                );
        Task updatedTask = taskMapper.updateDtoToEntity(dto);
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
    public UpdateAssigneeResponse assignTaskPerformers(AssigneeTaskForUserRequest dto) {
        Task existingTask = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(TASK_WITH_ID_S_NOT_FOUND_CONST, dto.getTaskId()))
                );
        userRepository.findById(dto.getAssigneeId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        USER_WITH_ID_S_NOT_FOUND_CONST, dto.getAssigneeId()))
                );
        return taskMapper.entityTaskToUpdateAssigneeDto(
                taskRepository.save(
                        taskMapper.buildAssigneeToSave(existingTask,
                                taskMapper.assigneeDtoToEntity(dto))));
    }

    @Override
    public boolean existsByTitle(String title) {
        return taskRepository.existsByTitle(title);
    }

    @Override
    public PageableResponse<TaskDataDto> getTaskByAuthorId(Long authorId, Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAllByAuthorId(authorId, pageable);
        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(taskPage.getContent());

        return pageableResponseUtil.buildPageableResponse(taskDataDtos, taskPage, new PageableResponse<>());
    }

    @Override
    public PageableResponse<TaskDataDto> getTaskByAssigneeId(Long assigneeId, Pageable pageable) {
        Page<Task> taskPage = taskRepository.findAllByAssigneeId(assigneeId, pageable);
        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(taskPage.getContent());

        return pageableResponseUtil.buildPageableResponse(taskDataDtos, taskPage, new PageableResponse<>());
    }
}
