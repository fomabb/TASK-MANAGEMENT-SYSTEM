package org.fomabb.taskmanagement.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.fomabb.taskmanagement.dto.CommentDataDto;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.TrackTimeDatDto;
import org.fomabb.taskmanagement.dto.response.TrackTimeResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
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
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateCommentDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generateListComment;
import static org.fomabb.taskmanagement.util.testobjectgenerator.comment.CommentResponseGenerator.generatePageCommentResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateListTaskDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateListTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generatePageTaskResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskDataDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TaskResponseGenerator.generateTaskResponseMap;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TrackTaskResponseGenerator.generateTrackResponse;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TrackTaskResponseGenerator.generateTrackTimeDataDtoFromTaskEntity;
import static org.fomabb.taskmanagement.util.testobjectgenerator.task.TrackTaskResponseGenerator.generateTrackTimeResponseMap;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
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
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(taskController).build();
        objectMapper = new ObjectMapper();
    }

    @Test
    void trackTimeWorks_ShouldReturnTrackTimeDatDto() throws Exception {
        // Arrange
        Task task = generateTaskEntity();
        TrackTimeDatDto trackTimeDatDto = generateTrackTimeDataDtoFromTaskEntity(task);

        when(taskService.trackTimeWorks(any(TrackTimeDatDto.class))).thenReturn(trackTimeDatDto);

        // Act
        mockMvc.perform(put("/api/v1/tasks/users/track-time").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(trackTimeDatDto))).andExpect(status().isAccepted()).andExpect(jsonPath("$.taskId").value(trackTimeDatDto.getTaskId())).andExpect(jsonPath("$.description").value(trackTimeDatDto.getDescription())).andExpect(jsonPath("$.timeTrack").value(trackTimeDatDto.getTimeTrack())).andExpect(jsonPath("$.dateTimeTrack").value(trackTimeDatDto.getDateTimeTrack()));

        // Assert
        verify(taskService, times(1)).trackTimeWorks(any(TrackTimeDatDto.class));
    }

    @Test
    void getTrackingBordByUserId_ShowReturnMapStringTrackTimeResponse() throws Exception {
        // Arrange
        Long userId = 1L;
        LocalDate startDate = LocalDate.of(2025, 12, 12);

        TrackTimeResponse trackTimeResponse = generateTrackResponse(generateTaskEntity());

        Map<String, List<TrackTimeResponse>> responseMap = generateTrackTimeResponseMap(trackTimeResponse, startDate);

        when(taskService.getTrackingBordByUserId(userId, startDate)).thenReturn(responseMap);

        // Act & Assert
        mockMvc.perform(get("/api/v1/tasks/user/{userId}/show-time-tracking-board", userId).param("startDate", startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk()).andExpect(jsonPath("$.['SUNDAY " + startDate.plusDays(6).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "'][0].description").value("Description-1"));

        verify(taskService, times(1)).getTrackingBordByUserId(userId, startDate);
    }

    @Test
    void getTasksByWeekday_ShouldReturnMapStringTaskDataDto() throws Exception {
        // Arrange
        LocalDate startDate = LocalDate.of(2025, 12, 12);
        TaskDataDto responseTaskDataDto = generateTaskDataDto();
        Map<String, List<TaskDataDto>> responseMap = generateTaskResponseMap(responseTaskDataDto, startDate);

        when(taskService.getTasksByWeekday(startDate)).thenReturn(responseMap);

        // Act & Assert
        mockMvc.perform(get("/api/v1/tasks/by-weekday").param("startDate", startDate.format(DateTimeFormatter.ISO_LOCAL_DATE)).contentType(MediaType.APPLICATION_JSON)).andExpect(jsonPath("$.['SUNDAY " + startDate.plusDays(6).format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + "'][0].description").value("Task Description"));

        verify(taskService, times(1)).getTasksByWeekday(startDate);
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
        mockMvc.perform(get("/api/v1/tasks").param("page", String.valueOf(page)).param("size", String.valueOf(size))).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size())).andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber())).andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize())).andExpect(jsonPath("$.content[0].id").value(expectedResponse.getContent().getFirst().getId())).andExpect(jsonPath("$.content[0].title").value(expectedResponse.getContent().getFirst().getTitle())).andExpect(jsonPath("$.content[0].description").value(expectedResponse.getContent().getFirst().getDescription()));

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
        mockMvc.perform(get("/api/v1/tasks/{taskId}", taskId)).andExpect(status().isOk()).andExpect(jsonPath("$.id").value(expectedTaskData.getId())).andExpect(jsonPath("$.title").value(expectedTaskData.getTitle())).andExpect(jsonPath("$.description").value(expectedTaskData.getDescription())).andExpect(jsonPath("$.priority").value(expectedTaskData.getPriority().name())).andExpect(jsonPath("$.status").value(expectedTaskData.getStatus().name()));

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
        mockMvc.perform(get("/api/v1/tasks/author/{authorId}", authorId).param("page", String.valueOf(page)).param("size", String.valueOf(size))).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size())).andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber())).andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));

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
        mockMvc.perform(get("/api/v1/tasks/comments/author/{authorId}", authorId).param("page", String.valueOf(page)).param("size", String.valueOf(size))).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size())).andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber())).andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));

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
        mockMvc.perform(get("/api/v1/tasks/assignee/{assigneeId}", assigneeId).param("page", String.valueOf(page)).param("size", String.valueOf(size))).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size())).andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber())).andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));


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
        mockMvc.perform(get("/api/v1/tasks/comments/by-taskId/{taskId}", taskId).param("page", String.valueOf(page)).param("size", String.valueOf(size))).andExpect(status().isOk()).andExpect(jsonPath("$.content").isArray()).andExpect(jsonPath("$.content.length()").value(expectedResponse.getContent().size())).andExpect(jsonPath("$.pageNumber").value(expectedResponse.getPageNumber())).andExpect(jsonPath("$.pageSize").value(expectedResponse.getPageSize()));

        // Assert
        verify(commentService, times(1)).getCommentsById(taskId, PageRequest.of(0, size));
    }
}
