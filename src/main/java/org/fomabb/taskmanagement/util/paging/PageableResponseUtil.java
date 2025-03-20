package org.fomabb.taskmanagement.util.paging;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * Утилитный класс для построения пагинированных ответов.
 */
public class PageableResponseUtil {

    /**
     * Создает пагинированный ответ на основе переданного содержимого и страницы.
     *
     * @param content список элементов, который будет содержаться в ответе
     * @param page объект {@link Page}, содержащий информацию о пагинации
     * @param response экземпляр класса, наследующего {@link PageableResponse}, который будет заполнен данными
     * @param <T> тип элементов, содержащихся в ответе
     * @param <R> тип класса, наследующего {@link PageableResponse}
     * @return заполненный экземпляр пагинированного ответа
     */
    public static <T, R extends PageableResponse<T>> R buildPageableResponse(List<T> content, Page<?> page, R response) {
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
