package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.UserDataDto;

import java.util.List;

/**
 * Интерфейс для сервиса управления пользователями.
 * Содержит методы для получения информации о пользователях.
 */
public interface AdminService {

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
    List<UserDataDto> getAllUser();
}
