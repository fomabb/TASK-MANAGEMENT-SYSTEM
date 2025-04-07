package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.TrackTimeDatDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.TrackTimeResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.TrackWorkTime;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.repository.TrackWorkTimeRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.security.service.UserServiceSecurity;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;
import org.fomabb.taskmanagement.util.pagable.PageableResponseUtil;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateAssigneeTaskRequest;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateAssigneeTaskUpdate;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateCreateTaskRequest;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateCreateTaskResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateEntityTaskToUpdateTaskDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateListTaskDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateListTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generatePageTaskResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TrackTaskResponseGenerator.generateListTrackTimeResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TrackTaskResponseGenerator.generateTrackTimeDataDtoToSave;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TrackTaskResponseGenerator.generateTrackWorkTimeEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TaskServiceImplTest {

    @Mock
    private TaskMapper taskMapper;

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private PageableResponseUtil responseUtil;

    @Mock
    private UserServiceSecurity userServiceSecurity;

    @Mock
    TrackWorkTimeRepository trackWorkTimeRepository;


    @Mock
    private UserRepository userRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @Test
    void trackTimeWorks_ShouldReturnTrackTimeDatDto() {
        // Arrange
        User currentUser = generateUserEntity();
        currentUser.setId(2L);

        Task task = generateTaskEntity();
        task.setScheduledTaskTime(5);
        task.setAssignee(currentUser);

        TrackTimeDatDto dto = TrackTimeDatDto.builder()
                .taskId(task.getId())
                .timeTrack(3)
                .dateTimeTrack("2025-04-07T17:54:00.478167700") // ISO формат даты
                .description("Tracking time test")
                .build();

        TrackWorkTime trackWorkTime = generateTrackTimeDataDtoToSave(dto);

        when(taskRepository.findById(dto.getTaskId())).thenReturn(Optional.of(task));
        when(userServiceSecurity.getCurrentUser()).thenReturn(currentUser);
        when(taskMapper.trackTimeWorkEntityToTrackTimeDto(trackWorkTime)).thenReturn(dto);
        when(taskMapper.buildTrackTimeDataDtoToSave(task, dto)).thenReturn(trackWorkTime);
        when(trackWorkTimeRepository.save(any())).thenReturn(trackWorkTime);

        // Act
        TrackTimeDatDto response = taskService.trackTimeWorks(dto);

        // Assert
        assertNotNull(response);
        assertEquals(dto.getDescription(), response.getDescription());
        assertEquals(dto.getTimeTrack(), response.getTimeTrack());
    }

    @Test
    void getTasksByWeekday_ShouldReturnMapStringTaskDataDto() {
        // Arrange
        LocalDate inputDate = LocalDate.of(2025, 12, 12);
        LocalDate startDate = inputDate.with(DayOfWeek.MONDAY);
        LocalDate endDate = inputDate.with(DayOfWeek.SUNDAY);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        List<Task> taskList = generateListTaskEntity();
        List<TaskDataDto> taskDataDtoList = generateListTaskDataDto();

        when(taskRepository.findTasksByCreatedAtBetween(startDateTime, endDateTime)).thenReturn(taskList);
        when(taskMapper.listEntityToListDto(taskList)).thenReturn(taskDataDtoList);

        // Act
        Map<String, List<TaskDataDto>> response = taskService.getTasksByWeekday(inputDate);

        // Assert
        assertNotNull(response);

        // Формируем ключ для проверки
        String expectedKey = String.format("%s %s", inputDate.getDayOfWeek().name(), inputDate.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        assertTrue(response.containsKey(expectedKey), "Expected key not found in response: " + expectedKey);

        List<TaskDataDto> returnedTaskData = response.get(expectedKey);
        assertNotNull(returnedTaskData);

        verify(taskMapper, times(1)).listEntityToListDto(taskList);
        verify(taskRepository, times(1)).findTasksByCreatedAtBetween(startDateTime, endDateTime);
    }

    @Test
    void getTrackingBordByUserId_ShouldMapStringTrackTimeResponse() {
        // Arrange
        Long userId = 1L;
        LocalDate inputDate = LocalDate.of(2025, 12, 12);
        LocalDate startDate = inputDate.with(DayOfWeek.MONDAY);
        LocalDate endDate = inputDate.with(DayOfWeek.SUNDAY);
        LocalDateTime startDateTime = startDate.atStartOfDay();
        LocalDateTime endDateTime = endDate.atTime(LocalTime.MAX);

        User userAssignee = generateUserEntity();
        Task task = generateTaskEntity();
        TrackWorkTime trackWorkTime = generateTrackWorkTimeEntity(task);
        List<TrackTimeResponse> trackTimeResponses = generateListTrackTimeResponse(task, inputDate);

        when(trackWorkTimeRepository.findByDateTimeTrackBetweenAndTaskAssignee(startDateTime, endDateTime, userAssignee))
                .thenReturn(List.of(trackWorkTime));
        when(userRepository.findById(userId)).thenReturn(Optional.of(userAssignee));
        when(taskMapper.listEntityTrackWorkTimeToTrackDto(List.of(trackWorkTime))).thenReturn(trackTimeResponses);

        // Assert
        Map<String, List<TrackTimeResponse>> response = taskService.getTrackingBordByUserId(userAssignee.getId(), inputDate);

        // Формируем ключ для проверки
        String expectedKey = String.format("%s %s", inputDate.getDayOfWeek().name(), inputDate.format(
                DateTimeFormatter.ofPattern("dd.MM.yyyy")));
        assertTrue(response.containsKey(expectedKey), "Expected key not found in response: " + expectedKey);

        List<TrackTimeResponse> returnTimeResponse = response.get(expectedKey);
        assertNotNull(returnTimeResponse);

        verify(trackWorkTimeRepository, times(1)).findByDateTimeTrackBetweenAndTaskAssignee(startDateTime, endDateTime, userAssignee);
        verify(userRepository, times(1)).findById(userId);
        verify(taskMapper, times(1)).listEntityTrackWorkTimeToTrackDto(List.of(trackWorkTime));
    }

    @Test
    void createTask_ShouldReturnCreatedTaskResponse() {
        // Arrange
        CreateTaskRequest request = generateCreateTaskRequest();

        User user = generateUserEntity();

        Task taskToSave = generateTaskEntity();

        CreatedTaskResponse expectedResponse = generateCreateTaskResponse(request);

        when(userRepository.findById(taskToSave.getAuthor().getId())).thenReturn(Optional.of(user));
        when(taskMapper.createRequestToEntity(request)).thenReturn(taskToSave);
        when(taskRepository.save(taskToSave)).thenReturn(taskToSave);
        when(taskMapper.entityToCreateResponse(taskToSave)).thenReturn(expectedResponse);

        // Act
        CreatedTaskResponse response = taskService.createTask(request);

        // Assert
        assertNotNull(response);
        assertEquals(expectedResponse.getId(), response.getId());
        assertEquals(expectedResponse.getTitle(), response.getTitle());
        assertEquals(expectedResponse.getDescription(), response.getDescription());
        assertEquals(expectedResponse.getStatus(), response.getStatus());
        assertEquals(expectedResponse.getPriority(), response.getPriority());
        verify(userRepository, times(1)).findById(taskToSave.getAuthor().getId());
        verify(taskMapper, times(1)).createRequestToEntity(request);
        verify(taskRepository, times(1)).save(taskToSave);
        verify(taskMapper, times(1)).entityToCreateResponse(taskToSave);
    }

    @Test
    void getAllTasks_ShouldReturnPageableTaskResponse() {

        // Arrange
        List<Task> taskList = generateListTaskEntity();
        List<TaskDataDto> taskDataDtoList = generateListTaskDataDto();
        Pageable pageable = PageRequest.of(1, 10);
        Page<Task> mockPage = new PageImpl<>(taskList, pageable, taskList.size());

        PageableResponse<TaskDataDto> pageableResponse = generatePageTaskResponse(taskDataDtoList, taskList);


        when(taskRepository.findAll(pageable)).thenReturn(mockPage);
        when(taskMapper.listEntityToListDto(taskList)).thenReturn(taskDataDtoList);
        when(responseUtil.buildPageableResponse(any(), any(), any())).thenReturn(pageableResponse);


        // Act
        PageableResponse<TaskDataDto> allTasks = taskService.getAllTasks(pageable);

        // Assert
        assertEquals(taskDataDtoList.size(), allTasks.getContent().size());
        assertEquals(1, allTasks.getPageNumber());

    }

    @Test
    void updateTaskForAdmin_ShouldReturnUpdateTaskDataDto() {

        // Arrange
        Task existingTask = generateTaskEntity();

        Task updatedTask = Task.builder().id(1L).title("Task Title").description("Task Description").priority(TaskPriority.LOW).status(TaskStatus.COMPLETED).createdAt(now()).updatedAt(now()).assignee(User.builder().id(2L).build()).author(User.builder().id(1L).build()).build();

        UpdateTaskDataDto updateTaskDataDto = generateEntityTaskToUpdateTaskDto(updatedTask);

        when(taskMapper.updateDtoToEntity(any())).thenReturn(updatedTask);
        when(taskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));
        when(taskRepository.save(any(Task.class))).thenReturn(updatedTask);
        when(taskMapper.buildUpdateTaskForSave(existingTask, updatedTask)).thenReturn(updatedTask);
        when(taskMapper.entityToUpdateDto(any(Task.class))).thenReturn(updateTaskDataDto);

        // Act
        UpdateTaskDataDto result = taskService.updateTaskForAdmin(updateTaskDataDto);

        // Assert
        assertNotNull(result);
        assertEquals(existingTask.getId(), result.getTaskId());
        assertNotEquals(existingTask.getStatus(), result.getStatus());
        verify(taskRepository, times(1)).findById(existingTask.getId());
        verify(taskRepository, times(1)).save(updatedTask);
    }

    @Test
    void getTaskById_ShouldReturnTaskDataDto() {
        // Arrange
        TaskDataDto taskDataDto = generateTaskDataDto();
        Task task = generateTaskEntity();

        when(taskRepository.findById(task.getId())).thenReturn(Optional.of(task));
        when(taskMapper.entityTaskToTaskDto(task)).thenReturn(taskDataDto);

        // Act
        TaskDataDto result = taskService.getTaskById(taskDataDto.getId());

        // Assert
        assertEquals(1, result.getId());
    }

    @Test
    void getTaskById_ShouldThrowEntityNotFoundException() {
        // Arrange
        Task task = generateTaskEntity();

        when(taskRepository.findById(task.getId())).thenReturn(Optional.empty());

        // Assert
        assertThrows(EntityNotFoundException.class, () -> taskService.getTaskById(task.getId()));
    }

    @Test
    void removeTaskById_ShouldRemoveTaskFromDB() {
        // Arrange
        Task task = generateTaskEntity();

        // Act
        taskService.removeTaskById(task.getId());

        // Assert
        verify(taskRepository, times(1)).deleteById(task.getId());
    }

    @Test
    void assignTaskPerformers_ShouldUpdateTaskAssigneeId() {

        // Arrange
        Task existingTask = generateTaskEntity();
        User user = generateUserEntity();

        AssigneeTaskForUserRequest request = generateAssigneeTaskRequest(existingTask);

        Task updatedTaskAssignee = Task.builder().id(existingTask.getId()).assignee(user).build();

        UpdateAssigneeResponse assigneeTaskUpdate = generateAssigneeTaskUpdate(existingTask);

        when(taskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));
        when(userRepository.findById(existingTask.getId())).thenReturn(Optional.of(user));
        when(taskMapper.assigneeDtoToEntity(any(AssigneeTaskForUserRequest.class))).thenReturn(updatedTaskAssignee);
        when(taskMapper.buildAssigneeToSave(existingTask, updatedTaskAssignee)).thenReturn(updatedTaskAssignee);
        when(taskRepository.save(updatedTaskAssignee)).thenReturn(updatedTaskAssignee);
        when(taskMapper.entityTaskToUpdateAssigneeDto(updatedTaskAssignee)).thenReturn(assigneeTaskUpdate);

        // Act
        UpdateAssigneeResponse result = taskService.assignTaskPerformers(request);

        // Assert
        assertNotNull(result);
        assertEquals(existingTask.getId(), result.getTaskId());
        assertNotEquals(existingTask.getAssignee().getId(), result.getAssigneeId());
        verify(taskRepository, times(1)).findById(existingTask.getId());
        verify(userRepository, times(1)).findById(user.getId());
        verify(taskRepository, times(1)).save(updatedTaskAssignee);
    }

    @Test
    void getTaskByAuthorId_ShouldReturnPageableTaskResponse() {

        // Arrange
        Long authorId = 1L;
        List<Task> taskList = generateListTaskEntity();
        List<TaskDataDto> taskDataDtoList = generateListTaskDataDto();
        Pageable pageable = PageRequest.of(1, 10);
        Page<Task> mockPage = new PageImpl<>(taskList, pageable, taskList.size());

        PageableResponse<TaskDataDto> pageableResponse = generatePageTaskResponse(taskDataDtoList, taskList);

        when(taskRepository.findAllByAuthorId(authorId, pageable)).thenReturn(mockPage);
        when(taskMapper.listEntityToListDto(taskList)).thenReturn(taskDataDtoList);
        when(responseUtil.buildPageableResponse(any(), any(), any())).thenReturn(pageableResponse);


        // Act
        PageableResponse<TaskDataDto> allTasks = taskService.getTaskByAuthorId(authorId, pageable);

        // Assert
        assertEquals(taskDataDtoList.size(), allTasks.getContent().size());
        assertEquals(1, allTasks.getPageNumber());
    }

    @Test
    void getTaskByAssigneeId_ShouldReturnPageableTaskResponse() {
        // Arrange
        Long assignee = 1L;
        List<Task> taskList = generateListTaskEntity();
        List<TaskDataDto> taskDataDtoList = generateListTaskDataDto();
        Pageable pageable = PageRequest.of(1, 10);
        Page<Task> mockPage = new PageImpl<>(taskList, pageable, taskList.size());

        PageableResponse<TaskDataDto> pageableResponse = generatePageTaskResponse(taskDataDtoList, taskList);

        when(taskRepository.findAllByAssigneeId(assignee, pageable)).thenReturn(mockPage);
        when(taskMapper.listEntityToListDto(taskList)).thenReturn(taskDataDtoList);
        when(responseUtil.buildPageableResponse(any(), any(), any())).thenReturn(pageableResponse);

        // Act
        PageableResponse<TaskDataDto> allTasks = taskService.getTaskByAssigneeId(assignee, pageable);

        // Assert
        assertEquals(taskDataDtoList.size(), allTasks.getContent().size());
        assertEquals(1, allTasks.getPageNumber());
    }
}
