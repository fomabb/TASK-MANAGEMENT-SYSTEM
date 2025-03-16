package com.iase24.test.dto.request;

import com.iase24.test.entity.enumeration.TaskPriority;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CreateTaskRequest {

    private String title;

    private String description;

    private TaskPriority priority;
}
