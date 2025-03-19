package org.fomabb.taskmanagement.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.fomabb.taskmanagement.dto.CommentsDataDto;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PaginCommentsResponse {

    private List<CommentsDataDto> content;
    private int pageNumber; // Номер страницы с 1
    private int pageSize;
    private boolean first;
    private boolean last;
    private int numberOfElements;
    private boolean empty;
}
