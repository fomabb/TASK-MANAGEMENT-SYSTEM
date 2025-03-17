CREATE TABLE tasks
(
    id          BIGSERIAL PRIMARY KEY,
    title       VARCHAR(255) NOT NULL,
    description TEXT,
    status      VARCHAR(50)  NOT NULL,
    priority    VARCHAR(50)  NOT NULL,
    author_id   BIGINT,
    assignee_id BIGINT,
    created_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at  TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (author_id) REFERENCES users (id),
    FOREIGN KEY (assignee_id) REFERENCES users (id)
);

comment on table tasks is 'Таблица для хранения задач';
comment on column tasks.id is 'Идентификационный номер задачи';
comment on column tasks.title is 'Заголовок задачи';
comment on column tasks.description is 'Описание задачи';
comment on column tasks.status is 'Статус задачи';
comment on column tasks.priority is 'Приоритет задачи';
comment on column tasks.author_id is 'Идентификационный номер автора задачи';
comment on column tasks.assignee_id is 'Идентификационный номер исполнителя задачи';
comment on column tasks.created_at is 'Время создания задачи';
comment on column tasks.updated_at is 'Время обновления задачи';
