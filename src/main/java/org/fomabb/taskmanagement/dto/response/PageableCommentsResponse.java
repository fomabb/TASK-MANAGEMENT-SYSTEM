package org.fomabb.taskmanagement.dto.response;

import lombok.Builder;
import org.fomabb.taskmanagement.dto.CommentDataDto;
import org.fomabb.taskmanagement.util.paging.PageableResponse;

@Builder
public class PageableCommentsResponse extends PageableResponse<CommentDataDto> {
}
