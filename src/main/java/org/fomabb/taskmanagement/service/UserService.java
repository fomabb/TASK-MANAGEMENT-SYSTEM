package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;

import java.util.List;

/**
 * Интерфейс для сервиса управления пользователями.
 * Содержит методы для получения информации о пользователях.
 */
public interface UserService {

    /**
     * Получает данные пользователя по его идентификатору.
     *
     * @param userId идентификатор пользователя, данные которого нужно получить
     * @return объект {@link UserDataDto}, содержащий данные пользователя
     */
    UserDataDto getUserById(Long userId);

    /**
     * Получает список всех пользователей.
     *
     * @return список объектов {@link UserDataDto}, содержащий данные всех пользователей
     */
    List<UserDataDto> getAllUsers();

    /**
     * Обновляет статус задачи для пользователя.
     *
     * @param requestBody объект {@link UpdateTaskForUserDataRequest}, содержащий данные для обновления статуса задачи
     * @return объект {@link UpdateTaskDataDto}, содержащий обновленные данные задачи
     */
    UpdateTaskDataDto updateTaskStatusForUser(UpdateTaskForUserDataRequest requestBody);

    /**
     * Получает список всех зарегистрированных пользователей в системе, исключая администраторов.
     *
     * <p>
     * Этот метод используется для получения информации о пользователях, которые не имеют
     * административных привилегий. Это может быть полезно для отображения списка пользователей
     * в различных интерфейсах, таких как задачи, команды и т.д.
     * </p>
     *
     * @return Список объектов {@link UserDataDto}, представляющих пользователей без администраторов.
     *         Если пользователей нет, возвращается пустой список.
     */
    List<UserDataDto> getAllRegularUsers();
}
