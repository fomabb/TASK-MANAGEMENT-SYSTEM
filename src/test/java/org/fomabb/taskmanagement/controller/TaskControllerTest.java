package org.fomabb.taskmanagement.controller;

import org.fomabb.taskmanagement.dto.CommentDataDto;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.service.CommentService;
import org.fomabb.taskmanagement.service.TaskService;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateCommentDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateListComment;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generatePageCommentResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateListTaskDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateListTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generatePageTaskResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskDataDto;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class TaskControllerTest {

    @Mock
    private TaskService taskService;

    @Mock
    private CommentService commentService;

    @InjectMocks
    private TaskController taskController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
    }

    @Test
    void getAllTask_ShouldReturnPageableResponse() throws Exception {
        // Arrange
        int page = 1;
        int size = 10;

        List<TaskDataDto> taskDataDtos = generateListTaskDataDto();
        PageableResponse<TaskDataDto> expectedResponse = generatePageTaskResponse(taskDataDtos, generateListTaskEntity());

        when(taskService.getAllTasks(PageRequest.of(0, size))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(get("/api/v1/tasks")
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size()))
                .andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber()))
                .andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()))
                .andExpect(jsonPath("$.content[0].id").value(expectedResponse.getContent().getFirst().getId()))
                .andExpect(jsonPath("$.content[0].title").value(expectedResponse.getContent().getFirst().getTitle()))
                .andExpect(jsonPath("$.content[0].description").value(expectedResponse.getContent().getFirst().getDescription()));

        // Assert
        verify(taskService, times(1)).getAllTasks(PageRequest.of(0, size));
    }

    @Test
    void getTaskById_ShouldReturnTaskDataDto() throws Exception {
        // Arrange
        Long taskId = 1L;
        TaskDataDto expectedTaskData = generateTaskDataDto();

        when(taskService.getTaskById(taskId)).thenReturn(expectedTaskData);

        // Act
        mockMvc.perform(get("/api/v1/tasks/{taskId}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(expectedTaskData.getId()))
                .andExpect(jsonPath("$.title").value(expectedTaskData.getTitle()))
                .andExpect(jsonPath("$.description").value(expectedTaskData.getDescription()))
                .andExpect(jsonPath("$.priority").value(expectedTaskData.getPriority().name()))
                .andExpect(jsonPath("$.status").value(expectedTaskData.getStatus().name()));

        // Assert
        verify(taskService, times(1)).getTaskById(taskId);
    }

    @Test
    void getTaskByAuthorId_ShouldReturnPageableResponse() throws Exception {
        // Arrange
        Long authorId = 1L;
        int page = 1;
        int size = 10;

        List<TaskDataDto> taskDataDtos = generateListTaskDataDto();
        PageableResponse<TaskDataDto> expectedResponse = generatePageTaskResponse(taskDataDtos, generateListTaskEntity());

        when(taskService.getTaskByAuthorId(authorId, PageRequest.of(0, size))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(get("/api/v1/tasks/author/{authorId}", authorId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size()))
                .andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber()))
                .andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));

        // Assert
        verify(taskService, times(1)).getTaskByAuthorId(authorId, PageRequest.of(0, size));
    }

    @Test
    void getCommentsByAuthorId_ShouldReturnPageableResponse() throws Exception {
        // Arrange
        Long authorId = 1L;
        int page = 1;
        int size = 10;

        List<Comment> commentList = generateListComment();
        List<CommentDataDto> commentDataDtos = generateCommentDataDto();

        PageableResponse<CommentDataDto> expectedResponse = generatePageCommentResponse(commentDataDtos, commentList);

        when(commentService.getCommentsByAuthorId(authorId, PageRequest.of(0, size))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(get("/api/v1/tasks/comments/author/{authorId}", authorId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size()))
                .andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber()))
                .andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));

        // Assert
        verify(commentService, times(1)).getCommentsByAuthorId(authorId, PageRequest.of(0, size));
    }

    @Test
    void getTaskByAssigneeId_ShouldReturnPageableResponse() throws Exception {
        // Arrange
        Long assigneeId = 1L;
        int page = 1;
        int size = 10;

        List<TaskDataDto> taskDataDtos = generateListTaskDataDto();
        PageableResponse<TaskDataDto> expectedResponse = generatePageTaskResponse(taskDataDtos, generateListTaskEntity());

        when(taskService.getTaskByAssigneeId(assigneeId, PageRequest.of(0, size))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(get("/api/v1/tasks/assignee/{assigneeId}", assigneeId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size()))
                .andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber()))
                .andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));


        // Assert
        verify(taskService, times(1)).getTaskByAssigneeId(assigneeId, PageRequest.of(0, size));
    }

    @Test
    void getCommentsById_ShouldReturnPageableResponse() throws Exception {
        // Arrange
        Long taskId = 1L;
        int page = 1;
        int size = 10;

        List<Comment> commentList = generateListComment();
        List<CommentDataDto> commentDataDtos = generateCommentDataDto();
        PageableResponse<CommentDataDto> expectedResponse = generatePageCommentResponse(commentDataDtos, commentList);

        when(commentService.getCommentsById(taskId, PageRequest.of(0, size))).thenReturn(expectedResponse);

        // Act
        mockMvc.perform(get("/api/v1/tasks/comments/by-taskId/{taskId}", taskId)
                        .param("page", String.valueOf(page))
                        .param("size", String.valueOf(size)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").isArray())
                .andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size()))
                .andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber()))
                .andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));

        // Assert
        verify(commentService, times(1)).getCommentsById(taskId, PageRequest.of(0, size));
    }
}
