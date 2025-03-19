package org.fomabb.taskmanagement.mapper;

import org.fomabb.taskmanagement.dto.UserDataDto;
import org.fomabb.taskmanagement.security.entity.User;

import java.util.List;

/**
 * Интерфейс для маппинга объектов пользователей.
 * Содержит методы для преобразования между сущностями пользователей и их DTO.
 */
public interface UserMapper {

    /**
     * Преобразует сущность пользователя в DTO пользователя.
     *
     * @param user объект {@link User}, который нужно преобразовать
     * @return объект {@link UserDataDto}, содержащий данные пользователя
     */
    UserDataDto entityUserToUserDto(User user);

    /**
     * Преобразует список сущностей пользователей в список DTO пользователей.
     *
     * @param users список объектов {@link User}, который нужно преобразовать
     * @return список объектов {@link UserDataDto}, содержащий данные пользователей
     */
    List<UserDataDto> listEntityUserToListUserDto(List<User> users);
}
