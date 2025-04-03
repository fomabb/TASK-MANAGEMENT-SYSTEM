package org.fomabb.taskmanagement.controller;

import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.fomabb.taskmanagement.entity.enumeration.TaskPriority;
import org.fomabb.taskmanagement.entity.enumeration.TaskStatus;
import org.fomabb.taskmanagement.service.impl.CommentServiceImpl;
import org.fomabb.taskmanagement.service.impl.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static java.time.LocalDateTime.now;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateListUserDto;
import static org.fomabb.taskmanagement.util.testobjectgenerator.user.UserDataDTOGenerator.generateUserDataDto;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
class UserControllerTest {

    @Mock
    private UserServiceImpl userService;

    @Mock
    private CommentServiceImpl commentService;

    @InjectMocks
    private UserController userController;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(userController).build();
    }

    @Test
    void getUserById_ShouldReturnUserDataDto() throws Exception {

        Long userId = 1L;
        UserDataDto userDataDto = generateUserDataDto();

        when(userService.getUserById(userId)).thenReturn(userDataDto);

        mockMvc.perform(get("/api/v1/user/" + userId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.userId").value(userId))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.userRole").value("ROLE_USER"));

        verify(userService, times(1)).getUserById(userId);
    }

    @Test
    void getAllUser_ShouldReturnListUserDataDto() throws Exception {

        List<UserDataDto> userDataDtos = generateListUserDto();

        when(userService.getAllUsers()).thenReturn(userDataDtos);

        mockMvc.perform(get("/api/v1/user/admin/show-all-users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(userDataDtos.size()));
    }

    @Test
    void updateTaskStatus_ShouldUpdateTaskDataDto() throws Exception {
        UpdateTaskForUserDataRequest request = UpdateTaskForUserDataRequest.builder()
                .taskId(1L)
                .taskStatus(TaskStatus.COMPLETED)
                .build();

        UpdateTaskDataDto updatedTaskDataDto = UpdateTaskDataDto.builder()
                .taskId(1L)
                .title("Title-Task-1")
                .description("Description-Task-2")
                .status(TaskStatus.COMPLETED)
                .priority(TaskPriority.HIGH)
                .build();

        when(userService.updateTaskStatusForUser(any(UpdateTaskForUserDataRequest.class)))
                .thenReturn(updatedTaskDataDto);

        mockMvc.perform(patch("/api/v1/user/tasks/update-status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskId\":1,\"status\":\"COMPLETED\",\"userId\":1}"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.taskId").value(1L))
                .andExpect(jsonPath("$.status").value("COMPLETED"));

        ArgumentCaptor<UpdateTaskForUserDataRequest> argumentCaptor = ArgumentCaptor.forClass(UpdateTaskForUserDataRequest.class);

        verify(userService, times(1)).updateTaskStatusForUser(argumentCaptor.capture());

        UpdateTaskForUserDataRequest capturedRequest = argumentCaptor.getValue();
        assertEquals(request.getTaskId(), capturedRequest.getTaskId());
    }

    @Test
    void addCommentToTaskById_CreateCommentAddToTaskDataDtoRequest() throws Exception {
        CommentAddToTaskDataDtoRequest request = CommentAddToTaskDataDtoRequest.builder()
                .taskId(101L)
                .authorCommentId(1L)
                .content("Это мой комментарий к задаче.")
                .build();

        CommentAddedResponse response = CommentAddedResponse.builder()
                .commentId(1L)
                .taskId(101L)
                .content("Это мой комментарий к задаче.")
                .authorFirstName("Иван")
                .authorLastName("Иванов")
                .updateComment(now())
                .build();

        when(commentService.addCommentToTaskById(any(CommentAddToTaskDataDtoRequest.class)))
                .thenReturn(response);

        mockMvc.perform(post("/api/v1/user/comments/post")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"taskId\":101,\"authorCommentId\":1,\"content\":\"Это мой комментарий к задаче.\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId").value(1L))
                .andExpect(jsonPath("$.taskId").value(101L))
                .andExpect(jsonPath("$.content").value("Это мой комментарий к задаче."))
                .andExpect(jsonPath("$.authorFirstName").value("Иван"))
                .andExpect(jsonPath("$.authorLastName").value("Иванов"));

        ArgumentCaptor<CommentAddToTaskDataDtoRequest> argumentCaptor = ArgumentCaptor.forClass(CommentAddToTaskDataDtoRequest.class);
        verify(commentService, times(1)).addCommentToTaskById(argumentCaptor.capture());

        CommentAddToTaskDataDtoRequest capturedRequest = argumentCaptor.getValue();
        assertEquals(request.getTaskId(), capturedRequest.getTaskId());
        assertEquals(request.getAuthorCommentId(), capturedRequest.getAuthorCommentId());
        assertEquals(request.getContent(), capturedRequest.getContent());
    }

    @Test
    void updateComment_ShouldReturnUpdatedCommentResponse() throws Exception {
        UpdateCommentRequest request = UpdateCommentRequest.builder()
                .commentId(1L)
                .newContent("Обновленный комментарий.")
                .build();

        UpdateCommentResponse response = UpdateCommentResponse.builder()
                .commentId(1L)
                .content("Обновленный комментарий.")
                .updateAt(now())
                .build();

        when(commentService.updateComment(any(UpdateCommentRequest.class)))
                .thenReturn(response);

        mockMvc.perform(patch("/api/v1/user/comments/update")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"commentId\":1,\"newContent\":\"Обновленный комментарий.\"}"))
                .andExpect(status().isAccepted())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.commentId").value(1L))
                .andExpect(jsonPath("$.content").value("Обновленный комментарий."));

        ArgumentCaptor<UpdateCommentRequest> argumentCaptor = ArgumentCaptor.forClass(UpdateCommentRequest.class);
        verify(commentService, times(1)).updateComment(argumentCaptor.capture());

        UpdateCommentRequest capturedRequest = argumentCaptor.getValue();
        assertEquals(request.getCommentId(), capturedRequest.getCommentId());
        assertEquals(request.getNewContent(), capturedRequest.getNewContent());
    }
}