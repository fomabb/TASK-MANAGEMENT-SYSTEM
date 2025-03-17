package com.iase24.test.dto;

import com.iase24.test.entity.Comment;
import com.iase24.test.entity.enumeration.TaskPriority;
import com.iase24.test.entity.enumeration.TaskStatus;
import com.iase24.test.security.entity.User;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@Schema(description = "Объект передачи данных задачи")
public class TaskDataDto {

    @Schema(description = "Идентификатор задачи", example = "1")
    private Long id;

    @Schema(description = "Заголовок задачи")
    private String title;

    @Schema(description = "Описание задачи")
    private String description;

    @Schema(description = "Время создания задачи")
    private LocalDateTime createdAt;

    @Schema(description = "Время обновления задачи")
    private LocalDateTime updatedAt;

    @Schema(description = "Статус задачи", example = """
            `PENDING`
            `IN_PROGRESS`
            `COMPLETED`
            """)
    private TaskStatus status;

    @Schema(description = "Приоритет задачи", example = """
            HIGH
            MEDIUM
            LOW
            """)
    private TaskPriority priority;

    @Schema(description = "Комментарии по задаче")
    private List<CommentsDataDto> comments;

    @Schema(description = "Автор задачи")
    private UserAuthorDataDto author;

    @Schema(description = "Исполнитель")
    private UserAssigneeDataDto assignee;
}
