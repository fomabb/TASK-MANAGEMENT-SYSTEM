CREATE TABLE users
(
    id         BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(255) NOT NULL,
    last_name  VARCHAR(255) NOT NULL,
    username   VARCHAR(255) NOT NULL UNIQUE,
    password   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL,
    created_at TIMESTAMP(6),
    updated_at TIMESTAMP(6)
);

INSERT INTO users(first_name, last_name, username, password, role, created_at, updated_at)
VALUES ('admin',
        'admin',
        'admin@gmail.com',
        '$2y$10$8iTw39LsZ6i4Fk/PPhnz6.PTPXpnlLZ4boeL7C2WkIz9FYohsWV16',
        'ROLE_ADMIN',
        now(), null);

comment on table users is 'Таблица для пользователей';
comment on column users.id is 'Идентификационный номер пользователя';
comment on column users.first_name is 'Имя пользователя';
comment on column users.last_name is 'Фамилия пользователя';
comment on column users.username is 'Логин пользователя';
comment on column users.password is 'Пароль пользователя';
comment on column users.role is 'Роль пользователя';
comment on column users.created_at is 'Время создания пользователя';
comment on column users.updated_at is 'Время обновления пользователя';