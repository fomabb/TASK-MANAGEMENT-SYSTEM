package org.fomabb.taskmanagement.service.impl;

import jakarta.persistence.EntityNotFoundException;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.util.pagable.PageableResponseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateAssigneeTaskRequest;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateAssigneeTaskUpdate;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateEntityTaskToUpdateTaskDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserEntity;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
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
    private UserRepository userRepository;

    @InjectMocks
    TaskServiceImpl taskService;

    @BeforeEach
    public void setUp() {
    }

    @BeforeAll
    public static void setUpBeforeAll() {
    }

    @Test
    void createTask_ShouldReturnCreatedTaskResponse() {

    }

    @Test
    void getAllTasks_ShouldReturnPageableTaskResponse() {

//        // Arrange
//        List<Task> taskList = generateListTaskEntity();
//        List<TaskDataDto> taskDataDtoList = generateListTaskDataDto();
//        Pageable pageable = PageRequest.of(1, 10);
//        Page<Task> mockPage = new PageImpl<>(taskList, pageable, taskList.size());
//
//        PageableResponse<TaskDataDto> expectedResponse = PageableResponse.<TaskDataDto>builder()
//                .content(taskDataDtoList)
//                .pageNumber(1) // Если сервис конвертирует 0-based → 1-based
//                .pageSize(10)
//                .isFirst(true)
//                .isLast(true)
//                .numberOfElements(taskList.size())
//                .isEmpty(false)
//                .totalPages(1)
//                .totalItems(taskList.size())
//                .build();
//
//        PageableResponse<TaskDataDto> pageableResponse = responseUtil.buildPageableResponse(taskDataDtoList, mockPage, expectedResponse);
//
//        when(taskRepository.findAll(pageable)).thenReturn(mockPage);
//        when(taskMapper.listEntityToListDto(taskList)).thenReturn(taskDataDtoList);
//        when(responseUtil.buildPageableResponse(taskDataDtoList, mockPage, expectedResponse)).thenReturn(pageableResponse);
//
//
//        // Act
//        PageableTaskResponse allTasks = taskService.getAllTasks(pageable);
//
//        // Assert
//        assertEquals(taskDataDtoList.size(), allTasks.getContent().size());
//        assertEquals(1, allTasks.getPageNumber());

    }

    @Test
    void updateTaskForAdmin_ShouldReturnUpdateTaskDataDto() {

        // Arrange
        Task existingTask = generateTaskEntity(); // Генерация существующей задачи

        Task updatedTask = Task.builder()
                .id(1L)
                .title("Task Title")
                .description("Task Description")
                .priority(TaskPriority.LOW)
                .status(TaskStatus.COMPLETED)
                .createdAt(now())
                .updatedAt(now())
                .assignee(User.builder().id(2L).build())
                .author(User.builder().id(1L).build())
                .build();

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

        Task updatedTaskAssignee = Task.builder()
                .id(existingTask.getId())
                .assignee(user)
                .build();

        UpdateAssigneeResponse assigneeTaskUpdate = generateAssigneeTaskUpdate(existingTask);

        when(taskRepository.findById(existingTask.getId())).thenReturn(Optional.of(existingTask));
        when(userRepository.findById(existingTask.getId())).thenReturn(Optional.of(user));
        when(taskMapper.assigneeDtoToEntity(any(AssigneeTaskForUserRequest.class))).thenReturn(updatedTaskAssignee);
        when(taskMapper.buildAssigneeToSave(existingTask, updatedTaskAssignee)).thenReturn(updatedTaskAssignee);
        when(taskRepository.save(updatedTaskAssignee)).thenReturn(updatedTaskAssignee);
        when(taskMapper.entityTaskToUpdateAssigneeDto(updatedTaskAssignee)).thenReturn(assigneeTaskUpdate); // Исправлено

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

    }

    @Test
    void getTaskByAssigneeId_ShouldReturnPageableTaskResponse() {

    }
}
