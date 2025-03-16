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