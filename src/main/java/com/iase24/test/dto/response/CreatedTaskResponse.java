package com.iase24.test.dto.response;

import com.iase24.test.entity.enumeration.TaskPriority;
import com.iase24.test.entity.enumeration.TaskStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreatedTaskResponse {

    private Long id;

    private String title;

    private String description;

    private TaskStatus status;

    private TaskPriority priority;
}
