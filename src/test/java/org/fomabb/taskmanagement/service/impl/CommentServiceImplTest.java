package org.fomabb.taskmanagement.service.impl;

import org.fomabb.taskmanagement.mapper.CommentMapper;
import org.fomabb.taskmanagement.repository.CommentRepository;
import org.fomabb.taskmanagement.repository.TaskRepository;
import org.fomabb.taskmanagement.security.repository.UserRepository;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class CommentServiceImplTest {


    @Mock
    private CommentMapper commentMapper;

    @Mock
    private CommentRepository commentRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private CommentServiceImpl commentService;

    @BeforeEach
    public void setUp() {

    }

    @BeforeAll
    public static void setUpBeforeAll() {

    }

    @Test
    void addCommentToTaskById_ShouldReturnCommentAddedResponse() {

    }

    @Test
    void updateComment_ShouldReturnUpdateCommentResponse() {

    }
}
