CREATE TABLE comments
(
    id         BIGSERIAL PRIMARY KEY,
    content    TEXT   NOT NULL,
    task_id    BIGINT NOT NULL,
    author_id  BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (task_id) REFERENCES tasks (id),
    FOREIGN KEY (author_id) REFERENCES users (id)
);

comment on table comments is 'Таблица для хранения комментариев';
comment on column comments.id is 'Идентификационный номер комментария';
comment on column comments.content is 'Контент комментария';
comment on column comments.task_id is 'Идентификационный номер задачи';
comment on column comments.author_id is 'Идентификационный номер автора';
comment on column comments.created_at is 'Время создания комментария';