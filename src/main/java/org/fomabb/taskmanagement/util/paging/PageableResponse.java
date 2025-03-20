package org.fomabb.taskmanagement.util.paging;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PageableResponse<T> {

    private List<T> content;
    private int pageNumber;
    private int pageSize;
    private boolean isFirst;
    private boolean isLast;
    private int numberOfElements;
    private boolean isEmpty;
    private int totalPages;
    private long totalItems;
}
