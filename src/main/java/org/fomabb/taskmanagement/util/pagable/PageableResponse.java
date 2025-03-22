package org.fomabb.taskmanagement.util.pagable;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Schema(description = "Общий ответ с пагинацией")
public class PageableResponse<T> {

    @Schema(description = "Содержимое текущей страницы")
    private List<T> content;

    @Schema(description = "Номер текущей страницы", example = "1")
    private int pageNumber;

    @Schema(description = "Размер страницы", example = "2")
    private int pageSize;

    @Schema(description = "Является ли текущая страница первой", example = "true")
    private boolean isFirst;

    @Schema(description = "Является ли текущая страница последней", example = "false")
    private boolean isLast;

    @Schema(description = "Количество элементов на текущей странице", example = "2")
    private int numberOfElements;

    @Schema(description = "Является ли текущая страница пустой", example = "false")
    private boolean isEmpty;

    @Schema(description = "Общее количество страниц", example = "5")
    private int totalPages;

    @Schema(description = "Общее количество элементов", example = "10")
    private long totalItems;
}
