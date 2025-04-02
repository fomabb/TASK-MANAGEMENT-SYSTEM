package org.fomabb.taskmanagement.service;

import jakarta.persistence.EntityNotFoundException;
import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.util.pagable.PageableResponse;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

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
     * @return объект {@link PageableResponse<TaskDataDto>}, содержащий список задач и информацию о пагинации
     */
    PageableResponse<TaskDataDto> getAllTasks(Pageable pageable);

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
    UpdateAssigneeResponse assignTaskPerformers(AssigneeTaskForUserRequest requestBody);

    /**
     * Проверяет, существует ли задача с указанным заголовком.
     *
     * @param title заголовок задачи, для проверки существования
     * @return true, если задача с таким заголовком существует, иначе false
     */
    boolean existsByTitle(String title);

    /**
     * Получает список задач, созданных автором с указанным ID.
     *
     * @param authorId ID автора, чьи задачи необходимо извлечь.
     * @param pageable объект, содержащий информацию о пагинации, включая номер страницы и размер страницы.
     * @return объект {@link PageableResponse<TaskDataDto>}, содержащий пагинированный список задач.
     * @throws EntityNotFoundException  если автор с указанным ID не найден.
     * @throws IllegalArgumentException если переданы неверные параметры пагинации.
     */
    PageableResponse<TaskDataDto> getTaskByAuthorId(Long authorId, Pageable pageable);

    /**
     * Получает список задач, назначенных исполнителю с указанным ID.
     *
     * @param assigneeId ID исполнителя, чьи задачи необходимо извлечь.
     * @param pageable   объект, содержащий информацию о пагинации, включая номер страницы и размер страницы.
     * @return объект {@link PageableResponse<TaskDataDto>}, содержащий пагинированный список задач.
     * @throws EntityNotFoundException  если исполнитель с указанным ID не найден.
     * @throws IllegalArgumentException если переданы неверные параметры пагинации.
     */
    PageableResponse<TaskDataDto> getTaskByAssigneeId(Long assigneeId, Pageable pageable);

    /**
     * Получает список задач, сгруппированных по дням недели.
     * <p>
     * Этот метод принимает дату, определяющую начало недели (понедельник), и возвращает
     * задачи, созданные с понедельника по воскресенье этой недели. Задачи
     * группируются по дням, что позволяет удобно просматривать их по дням недели.
     *
     * @param startDate дата, с которой начинается неделя (понедельник)
     * @return карта, где ключами являются дни недели (в виде строк), а значениями - списки задач
     * (TaskDataDto), созданных в соответствующий день. Если в какой-либо день не было
     * создано задач, соответствующий ключ будет иметь пустой список.
     */
    Map<String, List<TaskDataDto>> getTasksByWeekday(LocalDate startDate);
}