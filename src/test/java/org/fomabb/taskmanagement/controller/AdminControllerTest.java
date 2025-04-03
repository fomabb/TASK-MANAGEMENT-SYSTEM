package org.fomabb.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.facade.TaskFacade;
import org.fomabb.taskmanagement.security.entity.User;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateAddCommentRequest;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateCommentAddedResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateUpdateCommentResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateAssigneeTaskRequest;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateAssigneeTaskUpdate;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateCreateTaskRequest;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateCreateTaskResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateEntityTaskToUpdateTaskDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserEntity;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class AdminControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private TaskFacade taskFacade;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private AdminController adminController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(adminController).build();
    }

    @Test
    void createTask_ShouldReturnCreatedTaskResponse() throws Exception {
        // Arrange
        CreateTaskRequest request = generateCreateTaskRequest();

        CreatedTaskResponse expectedResponse = generateCreateTaskResponse(request);

        when(taskFacade.createTask(any(CreateTaskRequest.class))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(post("/api/v1/admin/tasks/create-task")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.title").value(expectedResponse.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedResponse.getDescription()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().name()))
                .andExpect(jsonPath("$.priority").value(expectedResponse.getPriority().name()));

        // Assert
        verify(taskFacade, times(1)).createTask(any(CreateTaskRequest.class));
    }

    @Test
    void updateTask_ShouldReturnUpdateTaskDataDto() throws Exception {

        // Arrange
        Task task = generateTaskEntity();

        UpdateTaskDataDto request = generateEntityTaskToUpdateTaskDto(task);

        UpdateTaskDataDto expectedResponse = generateEntityTaskToUpdateTaskDto(task);

        when(taskService.updateTaskForAdmin(any(UpdateTaskDataDto.class))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(patch("/api/v1/admin/tasks/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.taskId").value(expectedResponse.getTaskId()))
                .andExpect(jsonPath("$.status").value(expectedResponse.getStatus().name()));

        // Assert
        verify(taskService, times(1)).updateTaskForAdmin(any(UpdateTaskDataDto.class));
    }

    @Test
    void assignTaskPerformersByIdTask_ShouldReturnUpdateAssigneeResponse() throws Exception {
        // Arrange
        Task task = generateTaskEntity();

        AssigneeTaskForUserRequest request = generateAssigneeTaskRequest(task);

        UpdateAssigneeResponse expectedResponse = generateAssigneeTaskUpdate(task);

        when(taskService.assignTaskPerformers(any(AssigneeTaskForUserRequest.class))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(patch("/api/v1/admin/assignee-by-taskId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.taskId").value(expectedResponse.getTaskId()))
                .andExpect(jsonPath("$.title").value(expectedResponse.getTitle()))
                .andExpect(jsonPath("$.assigneeId").value(expectedResponse.getAssigneeId()))
                .andExpect(jsonPath("$.timeLeadTask").value(expectedResponse.getTimeLeadTask()));

        // Assert
        verify(taskService, times(1)).assignTaskPerformers(any(AssigneeTaskForUserRequest.class));
    }

    @Test
    void addCommentToTaskById_ShouldReturnCommentAddedResponse() throws Exception {
        // Arrange
        User user = generateUserEntity();

        CommentAddToTaskDataDtoRequest request = generateAddCommentRequest(user);

        CommentAddedResponse expectedResponse = generateCommentAddedResponse();

        when(commentService.addCommentToTaskById(any(CommentAddToTaskDataDtoRequest.class))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(post("/api/v1/admin/comments/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.commentId").value(expectedResponse.getCommentId()))
                .andExpect(jsonPath("$.taskId").value(expectedResponse.getTaskId()))
                .andExpect(jsonPath("$.content").value(expectedResponse.getContent()))
                .andExpect(jsonPath("$.authorFirstName").value(expectedResponse.getAuthorFirstName()))
                .andExpect(jsonPath("$.authorLastName").value(expectedResponse.getAuthorLastName()));

        // Assert
        verify(commentService, times(1)).addCommentToTaskById(any(CommentAddToTaskDataDtoRequest.class));
    }

    @Test
    void updateComment_ShouldReturnUpdateCommentResponse() throws Exception {

        // Arrange
        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .commentId(1L)
                .newContent("New Content")
                .build();

        UpdateCommentResponse expectedResponse = generateUpdateCommentResponse(request);

        when(commentService.updateComment(any(UpdateCommentRequest.class))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(patch("/api/v1/admin/comments/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(request)))
                .andExpect(status().isAccepted())
                .andExpect(jsonPath("$.commentId").value(expectedResponse.getCommentId()))
                .andExpect(jsonPath("$.content").value(expectedResponse.getContent()));

        // Assert
        verify(commentService, times(1)).updateComment(any(UpdateCommentRequest.class));
    }

    @Test
    void removeTaskById_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long taskId = 1L;

        doNothing().when(taskFacade).removeTaskById(taskId);

        // Act
        mockMvc.perform(delete("/api/v1/admin/tasks/{taskId}", taskId))
                .andExpect(status().isNoContent());

        // Assert
        verify(taskFacade, times(1)).removeTaskById(taskId);
    }

    @Test
    void removeCommentById_ShouldReturnNoContent() throws Exception {
        // Arrange
        Long commentId = 1L;

        doNothing().when(commentService).removeCommentById(commentId);

        // Act
        mockMvc.perform(delete("/api/v1/admin/comments/{commentId}", commentId))
                .andExpect(status().isNoContent());

        // Assert
        verify(commentService, times(1)).removeCommentById(commentId);
    }
}
