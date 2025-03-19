package org.fomabb.taskmanagement.mapper;

import org.fomabb.taskmanagement.dto.CommentsDataDto;
import org.fomabb.taskmanagement.dto.request.CommentAddToTaskDataDtoRequest;
import org.fomabb.taskmanagement.dto.response.CommentAddedResponse;
import org.fomabb.taskmanagement.entity.Comment;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.security.entity.User;

/**
 * Интерфейс для маппинга объектов комментариев.
 * Содержит методы для преобразования между DTO и сущностями комментариев.
 */
public interface CommentMapper {

    /**
     * Преобразует данные из DTO в сущность комментария.
     *
     * @param userExist существующий пользователь, связанный с комментарием
     * @param taskExist существующая задача, к которой относится комментарий
     * @param dto объект {@link CommentAddToTaskDataDtoRequest}, содержащий данные для создания комментария
     * @return объект {@link Comment}, созданный на основе данных из DTO
     */
    Comment commentDtoToCommentEntity(User userExist, Task taskExist, CommentAddToTaskDataDtoRequest dto);

    /**
     * Преобразует сущность комментария в DTO для добавленного комментария.
     *
     * @param comment объект {@link Comment}, который нужно преобразовать
     * @return объект {@link CommentAddedResponse}, содержащий данные добавленного комментария
     */
    CommentAddedResponse entityCommentToCommentAddedDto(Comment comment);

    /**
     * Преобразует сущность комментария в DTO для отображения данных комментария.
     *
     * @param comment объект {@link Comment}, который нужно преобразовать
     * @return объект {@link CommentsDataDto}, содержащий данные комментария
     */
    CommentsDataDto entityCommentToCommentDto(Comment comment);
}