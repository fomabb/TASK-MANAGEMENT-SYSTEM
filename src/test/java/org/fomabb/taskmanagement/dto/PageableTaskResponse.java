package org.fomabb.taskmanagement.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class PageableTaskResponse {

    private List<Object> content;

    private int pageNumber;

    private int pageSize;

    private boolean isFirst;

    private boolean isLast;

    private int numberOfElements;

    private boolean isEmpty;

    private int totalPages;

    private long totalItems;
}
