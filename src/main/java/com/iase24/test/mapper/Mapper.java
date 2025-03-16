package com.iase24.test.mapper;

import com.iase24.test.dto.request.CreateTaskRequest;
import com.iase24.test.dto.response.CreatedTaskResponse;
import com.iase24.test.entity.Task;

public interface Mapper {

    CreatedTaskResponse entityToCreateResponse(Task task);

    Task CreateRequestToEntity(CreateTaskRequest request);
}
