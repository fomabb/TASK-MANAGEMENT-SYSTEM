package org.fomabb.taskmanagement.dto.response;

import lombok.Getter;
import lombok.Setter;
import org.fomabb.taskmanagement.dto.CommentsDataDto;
import org.fomabb.taskmanagement.util.paging.PaginResponse;

import java.util.List;

@Getter
@Setter
public class PaginCommentsResponse extends PaginResponse<CommentsDataDto> {

    private List<CommentsDataDto> content;
    private int pageNumber;
    private int pageSize;
    private boolean first;
    private boolean last;
    private int numberOfElements;
    private boolean isEmpty;
    private int totalPages;
    private long totalItems;
}
