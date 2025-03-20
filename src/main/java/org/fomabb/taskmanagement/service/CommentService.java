package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.request.UpdateCommentRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.dto.response.PageableCommentsResponse;
import org.fomabb.taskmanagement.dto.response.UpdateCommentResponse;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс для сервиса комментариев.
 * Содержит методы для управления комментариями к задачам.
 */
public interface CommentService {

    /**
     * Добавляет комментарий к задаче по её идентификатору.
     *
     * @param requestBody объект {@link CommentAddToTaskDataDtoRequest}, содержащий данные комментария
     * @return объект {@link CommentAddedResponse}, содержащий информацию о добавленном комментарии
     */
    CommentAddedResponse addCommentToTaskById(CommentAddToTaskDataDtoRequest requestBody);

    /**
     * Обновляет существующий комментарий.
     *
     * @param requestBody объект {@link UpdateCommentRequest}, содержащий данные для обновления комментария
     * @return объект {@link UpdateCommentResponse}, содержащий информацию об обновленном комментарии
     */
    UpdateCommentResponse updateComment(UpdateCommentRequest requestBody);

    /**
     * Получает список комментариев по идентификатору задачи с пагинацией.
     *
     * @param taskId идентификатор задачи, для которой нужно получить комментарии
     * @param pageable объект {@link Pageable}, содержащий параметры пагинации
     * @return объект {@link PageableCommentsResponse}, содержащий список комментариев и информацию о пагинации
     */
    PageableCommentsResponse getCommentsById(Long taskId, Pageable pageable);

    PageableCommentsResponse getCommentsByAuthorId(Long authorId, Pageable pageable);
}
