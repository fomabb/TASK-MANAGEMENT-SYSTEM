package org.fomabb.taskmanagement.service;

import jakarta.persistence.EntityNotFoundException;
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

    /**
     * Получает список комментариев, созданных автором с указанным ID.
     *
     * @param authorId ID автора, чьи комментарии необходимо извлечь.
     * @param pageable объект, содержащий информацию о пагинации, включая номер страницы и размер страницы.
     * @return объект {@link PageableCommentsResponse}, содержащий пагинированный список комментариев.
     * @throws EntityNotFoundException если автор с указанным ID не найден.
     * @throws IllegalArgumentException если переданы неверные параметры пагинации.
     */
    PageableCommentsResponse getCommentsByAuthorId(Long authorId, Pageable pageable);

    /**
     * Удаляет комментарий по указанному идентификатору.
     *
     * Этот метод проверяет существование комментария с заданным идентификатором.
     * Если комментарий существует, он будет удален из базы данных.
     * Если комментарий не найден, будет выброшено исключение {@link EntityNotFoundException}.
     *
     * @param commentId идентификатор комментария, который необходимо удалить.
     * @throws EntityNotFoundException если комментарий с указанным идентификатором не найден.
     */
    void removeCommentById(Long commentId);
}
