create table track_works_times
(
    id              bigserial primary key,
    description     text,
    time_track      INTEGER,
    date_time_track timestamp,
    task_id         bigint,
    foreign key (task_id) references tasks (id)
);

comment on table track_works_times is 'Таблица для хранения фиксации рабочего времени';
comment on column track_works_times.id is 'Идентификационный номер таблицы';
comment on column track_works_times.description is 'Доклад о проделанной работе';
comment on column track_works_times.time_track is 'Затраченное время на выполнения задачи';
comment on column track_works_times.date_time_track is 'Дата и время создания';
comment on column track_works_times.task_id is 'Ссылка на задачу';