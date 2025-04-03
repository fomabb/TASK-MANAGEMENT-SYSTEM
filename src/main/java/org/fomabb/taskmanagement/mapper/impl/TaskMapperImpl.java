package org.fomabb.taskmanagement.mapper.impl;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.TrackTimeDatDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserAssigneeDataDto;
import org.fomabb.taskmanagement.dto.UserAuthorDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.TrackTimeResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.TrackWorkTime;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.exceptionhandler.exeption.BusinessException;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.security.entity.User;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Collections;
import java.util.List;

import static java.time.LocalDateTime.now;

@Component
public class TaskMapperImpl implements TaskMapper {

    @Override
    public CreatedTaskResponse entityToCreateResponse(Task task) {
        return CreatedTaskResponse.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .build();
    }

    @Override
    public Task createRequestToEntity(CreateTaskRequest dto) {
        return Task.builder()
                .author(User.builder().id(dto.getAuthorId()).build())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .priority(dto.getPriority())
                .createdAt(now())
                .status(TaskStatus.PENDING)
                .updatedAt(null)
                .build();
    }

    @Override
    public List<TaskDataDto> listEntityToListDto(List<Task> tasks) {
        if (tasks == null) {
            return Collections.emptyList();
        }
        return tasks.stream().map(this::entityTaskToTaskDto).toList();
    }

    @Override
    public List<TrackTimeResponse> listEntityTrackWorkTimeToTrackDto(List<TrackWorkTime> track) {
        if (track == null) {
            return Collections.emptyList();
        }

        return track.stream()
                .map(trackWorkTime -> TrackTimeResponse.builder()
                        .taskId(trackWorkTime.getId())
                        .dateTimeTrack(trackWorkTime.getDateTimeTrack())
                        .timeTrack(trackWorkTime.getTimeTrack())
                        .description(trackWorkTime.getDescription())
                        .build())
                .toList();
    }

    @Override
    public TaskDataDto entityTaskToTaskDto(Task task) {
        return TaskDataDto.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .priority(task.getPriority())
                .status(task.getStatus())
                .createdAt(task.getCreatedAt())
                .updatedAt(task.getUpdatedAt())
                .assignee(task.getAssignee() != null ?
                        UserAssigneeDataDto.builder().assigneeId(task.getAssignee().getId()).build() : null)
                .author(UserAuthorDataDto.builder().authorId(task.getAuthor().getId()).build())
                .timeLeadTask(task.getTimeLeadTask())
                .build();
    }

    @Override
    public UpdateTaskDataDto entityToUpdateDto(Task task) {
        return UpdateTaskDataDto.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .status(task.getStatus())
                .priority(task.getPriority())
                .build();
    }

    @Override
    public UpdateAssigneeResponse entityTaskToUpdateAssigneeDto(Task task) {
        return UpdateAssigneeResponse.builder()
                .taskId(task.getId())
                .title(task.getTitle())
                .updatedAt(task.getUpdatedAt())
                .assigneeId(task.getAssignee().getId())
                .timeLeadTask(task.getTimeLeadTask())
                .build();
    }

    @Override
    public Task updateDtoToEntity(UpdateTaskDataDto dto) {
        return Task.builder()
                .id(dto.getTaskId())
                .title(dto.getTitle())
                .description(dto.getDescription())
                .status(dto.getStatus())
                .priority(dto.getPriority())
                .updatedAt(now())
                .build();
    }

    @Override
    public Task assigneeDtoToEntity(AssigneeTaskForUserRequest dto) {
        return Task.builder()
                .id(dto.getTaskId())
                .assignee(User.builder().id(dto.getAssigneeId()).build())
                .timeLeadTask(dto.getTimeLeadTask())
                .build();
    }


    @Override
    public Task buildUpdateTaskForSave(Task existingTask, Task updatedTask) {
        return Task.builder()
                .id(existingTask.getId())
                .createdAt(existingTask.getCreatedAt())
                .author(existingTask.getAuthor())
                .assignee(existingTask.getAssignee())
                .comments(existingTask.getComments())
                .title(updatedTask.getTitle() != null ? updatedTask.getTitle() : existingTask.getTitle())
                .description(updatedTask.getDescription() != null ? updatedTask.getDescription() : existingTask.getDescription())
                .status(updatedTask.getStatus() != null ? updatedTask.getStatus() : existingTask.getStatus())
                .priority(updatedTask.getPriority() != null ? updatedTask.getPriority() : existingTask.getPriority())
                .updatedAt(now())
                .build();
    }

    @Override
    public Task buildAssigneeToSave(Task existingTask, Task assigneeTask) {
        return Task.builder()
                .id(existingTask.getId())
                .title(existingTask.getTitle())
                .description(existingTask.getDescription())
                .author(existingTask.getAuthor())
                .status(existingTask.getStatus())
                .priority(existingTask.getPriority())
                .createdAt(existingTask.getCreatedAt())
                .updatedAt(existingTask.getUpdatedAt())
                .author(existingTask.getAuthor())
                .assignee(assigneeTask.getAssignee())
                .timeLeadTask(assigneeTask.getTimeLeadTask())
                .build();
    }

    @Override
    public TrackWorkTime buildTrackTimeDataDtoToSave(Task task, TrackTimeDatDto dto) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
        LocalDateTime dateTimeTrack;

        try {
            LocalDate date = LocalDate.parse(dto.getDateTimeTrack(), formatter);
            dateTimeTrack = date.atStartOfDay();
        } catch (DateTimeParseException e) {
            System.err.println("Date parsing error: " + e.getMessage());
            throw new BusinessException("Invalid date format. Please use dd.MM.yyyy.");
        }

        return TrackWorkTime.builder()
                .task(Task.builder().id(dto.getTaskId()).build())
                .dateTimeTrack(dateTimeTrack)
                .description(dto.getDescription())
                .timeTrack(dto.getTimeTrack())
                .build();
    }
}