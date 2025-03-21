package org.fomabb.taskmanagement.service.impl;

import org.fomabb.taskmanagement.mapper.TaskMapper;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.fomabb.taskmanagement.util.paging.PageableResponseUtil;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

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

    }

    @Test
    void updateTaskForAdmin_ShouldReturnUpdateTaskDataDto() {

    }

    @Test
    void getTaskById_ShouldReturnTaskDataDto() {

    }

    @Test
    void removeTaskById_ShouldRemoveTaskFromDB() {

    }

    @Test
    void assignTaskPerformers_ShouldUpdateTaskAssigneeId() {

    }

    @Test
    void getTaskByAuthorId_ShouldReturnPageableTaskResponse() {

    }

    @Test
    void getTaskByAssigneeId_ShouldReturnPageableTaskResponse() {

    }
}
