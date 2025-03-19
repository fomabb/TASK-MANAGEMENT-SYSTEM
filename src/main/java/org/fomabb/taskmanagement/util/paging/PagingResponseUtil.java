package org.fomabb.taskmanagement.util.paging;

import org.springframework.data.domain.Page;

import java.util.List;

public class PagingResponseUtil {

    public static <T, R extends PaginResponse<T>> R buildPagingResponse(List<T> content, Page<?> page, R response) {
        response.setContent(content);
        response.setPageNumber(page.getNumber() + 1);
        response.setPageSize(page.getSize());
        response.setFirst(page.isFirst());
        response.setLast(page.isLast());
        response.setNumberOfElements(page.getNumberOfElements());
        response.setEmpty(page.isEmpty());
        response.setTotalPages(page.getTotalPages());
        response.setTotalItems(page.getTotalElements());
        return response;
    }
}
