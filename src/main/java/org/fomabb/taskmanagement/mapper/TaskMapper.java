package org.fomabb.taskmanagement.mapper;

import org.fomabb.taskmanagement.dto.TaskDataDto;
import org.fomabb.taskmanagement.dto.TrackTimeDatDto;
import org.fomabb.taskmanagement.dto.UpdateTaskDataDto;
import org.fomabb.taskmanagement.dto.request.AssigneeTaskForUserRequest;
import org.fomabb.taskmanagement.dto.request.CreateTaskRequest;
import org.fomabb.taskmanagement.dto.response.CreatedTaskResponse;
import org.fomabb.taskmanagement.dto.response.TrackTimeResponse;
import org.fomabb.taskmanagement.dto.response.UpdateAssigneeResponse;
import org.fomabb.taskmanagement.entity.Task;
import org.fomabb.taskmanagement.entity.TrackWorkTime;

import java.util.List;

/**
 * Интерфейс для маппинга объектов задач.
 * Содержит методы для преобразования между DTO и сущностями задач.
 */
public interface TaskMapper {

    /**
     * Преобразует сущность задачи в ответ для создания задачи.
     *
     * @param task объект {@link Task}, который нужно преобразовать
     * @return объект {@link CreatedTaskResponse}, содержащий данные созданной задачи
     */
    CreatedTaskResponse entityToCreateResponse(Task task);

    /**
     * Преобразует запрос на создание задачи в сущность задачи.
     *
     * @param dto объект {@link CreateTaskRequest}, содержащий данные для создания задачи
     * @return объект {@link Task}, созданный на основе данных из запроса
     */
    Task createRequestToEntity(CreateTaskRequest dto);

    /**
     * Преобразует список сущностей задач в список DTO задач.
     *
     * @param tasks список объектов {@link Task}, который нужно преобразовать
     * @return список объектов {@link TaskDataDto}, содержащий данные задач
     */
    List<TaskDataDto> listEntityToListDto(List<Task> tasks);

    List<TrackTimeResponse> listEntityTrackWorkTimeToTrackDto(List<TrackWorkTime> track);

    /**
     * Преобразует сущность задачи в DTO для отображения данных задачи.
     *
     * @param task объект {@link Task}, который нужно преобразовать
     * @return объект {@link TaskDataDto}, содержащий данные задачи
     */
    TaskDataDto entityTaskToTaskDto(Task task);

    /**
     * Преобразует сущность задачи в DTO для обновления данных задачи.
     *
     * @param task объект {@link Task}, который нужно преобразовать
     * @return объект {@link UpdateTaskDataDto}, содержащий данные для обновления задачи
     */
    UpdateTaskDataDto entityToUpdateDto(Task task);

    UpdateAssigneeResponse entityTaskToUpdateAssigneeDto(Task task);

    /**
     * Преобразует DTO для обновления задачи в сущность задачи.
     *
     * @param dto объект {@link UpdateTaskDataDto}, содержащий данные для обновления задачи
     * @return объект {@link Task}, созданный на основе данных из DTO
     */
    Task updateDtoToEntity(UpdateTaskDataDto dto);

    /**
     * Обновляет существующую задачу с использованием данных из обновленной задачи.
     *
     * @param existingTask существующая задача, которую нужно обновить
     * @param updatedTask задача с обновленными данными
     * @return объект {@link Task}, обновленный на основе данных из обновленной задачи
     */
    Task buildUpdateTaskForSave(Task existingTask, Task updatedTask);

    /**
     * Преобразует DTO назначенного пользователя в сущность задачи.
     *
     * @param dto объект {@link AssigneeTaskForUserRequest}, содержащий данные для назначения пользователя
     * @return объект {@link Task}, созданный на основе данных из DTO
     */
    Task assigneeDtoToEntity(AssigneeTaskForUserRequest dto);

    /**
     * Обновляет задачу с назначением нового пользователя.
     *
     * @param existingTask  существующая задача, к которой нужно добавить назначение
     * @param assigneeTask задача с данными о назначении
     * @return объект {@link Task}, обновленный с новым назначением
     */
    Task buildAssigneeToSave(Task existingTask, Task assigneeTask);

    TrackWorkTime buildTrackTimeDataDtoToSave(Task task, TrackTimeDatDto dto);

    TrackTimeDatDto trackTimeWorkEntityToTrackTimeDto(TrackWorkTime entity);
}
