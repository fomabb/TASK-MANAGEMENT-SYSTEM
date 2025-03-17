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