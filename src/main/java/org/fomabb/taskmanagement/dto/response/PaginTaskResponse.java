package org.fomabb.taskmanagement.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.paging.PaginResponse;

import java.util.List;

@Getter
@Setter
public class PaginTaskResponse extends PaginResponse<TaskDataDto> {

    private List<TaskDataDto> content;
    private int pageNumber;
    private int pageSize;
    private boolean first;
    private boolean last;
    private int numberOfElements;
    private boolean isEmpty;
    private int totalPages;
    private long totalItems;
}
