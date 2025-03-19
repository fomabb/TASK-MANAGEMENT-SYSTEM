package org.fomabb.taskmanagement.service;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.request.UpdateTaskForUserDataRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.PaginTaskResponse;
import org.springframework.data.domain.Pageable;

/**
 * Интерфейс для сервиса задач.
 * Содержит методы для управления задачами и их состоянием.
 */
public interface TaskService {

    /**
     * Создает новую задачу.
     *
     * @param requestBody объект {@link CreateTaskRequest}, содержащий данные для создания задачи
     * @return объект {@link CreatedTaskResponse}, содержащий информацию о созданной задаче
     */
    CreatedTaskResponse createTask(CreateTaskRequest requestBody);

    /**
     * Получает список всех задач с пагинацией.
     *
     * @param pageable объект {@link Pageable}, содержащий параметры пагинации
     * @return объект {@link PaginTaskResponse}, содержащий список задач и информацию о пагинации
     */
    PaginTaskResponse getAllTasks(Pageable pageable);

    /**
     * Обновляет данные задачи для администратора.
     *
     * @param requestBody объект {@link UpdateTaskDataDto}, содержащий данные для обновления задачи
     * @return объект {@link UpdateTaskDataDto}, содержащий обновленные данные задачи
     */
    UpdateTaskDataDto updateTaskForAdmin(UpdateTaskDataDto requestBody);

    /**
     * Получает задачу по её идентификатору.
     *
     * @param id идентификатор задачи, которую нужно получить
     * @return объект {@link TaskDataDto}, содержащий данные задачи
     */
    TaskDataDto getTaskById(Long id);

    /**
     * Удаляет задачу по её идентификатору.
     *
     * @param id идентификатор задачи, которую нужно удалить
     */
    void removeTaskById(Long id);

    /**
     * Назначает исполнителей для задачи.
     *
     * @param requestBody объект {@link AssigneeTaskForUserRequest}, содержащий данные для назначения исполнителей
     */
    void assignTaskPerformers(AssigneeTaskForUserRequest requestBody);

    /**
     * Проверяет, существует ли задача с указанным заголовком.
     *
     * @param title заголовок задачи, для проверки существования
     * @return true, если задача с таким заголовком существует, иначе false
     */
    boolean existsByTitle(String title);

    /**
     * Обновляет статус задачи для пользователя.
     *
     * @param requestBody объект {@link UpdateTaskForUserDataRequest}, содержащий данные для обновления статуса задачи
     * @return объект {@link UpdateTaskDataDto}, содержащий обновленные данные задачи
     */
    UpdateTaskDataDto updateTaskStatusForUser(UpdateTaskForUserDataRequest requestBody);
}