package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.TrackTimeDatDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.TrackTimeResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.exceptionhandler.exeption.BusinessException;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.repository.TrackWorkTimeRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.security.service.UserServiceSecurity;
import org.fomabb.taskmanagement.service.TaskService;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;
import org.fomabb.taskmanagement.util.pagable.PageableResponseUtil;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.fomabb.taskmanagement.util.ConstantProject.TASK_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.USER_WITH_ID_S_NOT_FOUND_CONST;
import static org.fomabb.taskmanagement.util.ConstantProject.VALIDATION_REFERENCES_CONST;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TaskServiceImpl implements TaskService {

    private final TaskMapper taskMapper;
    private final TaskRepository taskRepository;
    private final TrackWorkTimeRepository trackWorkTimeRepository;
    private final UserRepository userRepository;
    private final UserServiceSecurity userServiceSecurity;
    private final PageableResponseUtil pageableResponseUtil;

    @Override
    public Map<String, List<TaskDataDto>> getTasksByWeekday(LocalDate inputDate) {
        LocalDate startDate = inputDate.with(DayOfWeek.MONDAY);
        LocalDate endDate = inputDate.with(DayOfWeek.SUNDAY);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<TaskDataDto> taskDataDtos = taskMapper.listEntityToListDto(
                taskRepository.findTasksByCreatedAtBetween(startDateTime, endDateTime)
        );

        Map<String, List<TaskDataDto>> tasksByWeekday = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            LocalDate date = startDate.with(day);
            String key = String.format("%s %s", day.name(), date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            tasksByWeekday.put(key, new ArrayList<>());
        }

        for (TaskDataDto taskDataDto : taskDataDtos) {
            LocalDate taskDate = taskDataDto.getCreatedAt().toLocalDate();
            String key = String.format("%s %s", taskDate.getDayOfWeek().name(), taskDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            tasksByWeekday.get(key).add(taskDataDto);
        }

        return tasksByWeekday;
    }

    @Override
    @Transactional
    public TrackTimeDatDto trackTimeWorks(TrackTimeDatDto dto) {
        Task task = taskRepository.findById(dto.getTaskId())
                .orElseThrow(() -> new EntityNotFoundException(String.format(
                        TASK_WITH_ID_S_NOT_FOUND_CONST, dto.getTaskId())));
        Long currentId = userServiceSecurity.getCurrentUser().getId();

        if (Objects.equals(task.getAssignee().getId(), currentId)) {
            trackWorkTimeRepository.save(taskMapper.buildTrackTimeDataDtoToSave(task, dto));
        } else {
            throw new BusinessException(VALIDATION_REFERENCES_CONST);
        }

        return TrackTimeDatDto.builder()
                .taskId(dto.getTaskId())
                .description(dto.getDescription())
                .timeTrack(dto.getTimeTrack())
                .dateTimeTrack(dto.getDateTimeTrack())
                .build();
    }

    @Override
    public Map<String, List<TrackTimeResponse>> getTrackingBordByUserId(Long userId, LocalDate inputDate) {
        LocalDate startDate = inputDate.with(DayOfWeek.MONDAY);
        LocalDate endDate = inputDate.with(DayOfWeek.SUNDAY);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        User taskAssignee = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(USER_WITH_ID_S_NOT_FOUND_CONST, userId)));

        List<TrackTimeResponse> trackTimeResponses =
                taskMapper.listEntityTrackWorkTimeToTrackDto(
                        trackWorkTimeRepository.findByDateTimeTrackBetweenAndTaskAssignee(
                                startDateTime, endDateTime, taskAssignee)
                );

        Map<String, List<TrackTimeResponse>> trackByWeekDay = new LinkedHashMap<>();

        for (DayOfWeek day : DayOfWeek.values()) {
            LocalDate date = startDate.with(day);
            String key = String.format("%s %s", day.name(), date.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            trackByWeekDay.put(key, new ArrayList<>());
        }

        for (TrackTimeResponse track : trackTimeResponses) {
            LocalDate taskDate = track.getDateTimeTrack().toLocalDate();
            String key = String.format("%s %s", taskDate.getDayOfWeek().name(), taskDate.format(DateTimeFormatter.ofPattern("dd.MM.yyyy")));
            trackByWeekDay.get(key).add(track);
        }

        return trackByWeekDay;
    }

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
