# Система управления задачами

# Полноценное веб-приложение REST написано с использованием следующих технологий:

- Maven
- Hibernate
- JPA
- PostgresSQL
- Spring Boot
- Spring security
- Docker container
- Liquibase

### Краткое описание проекта

- Автономное приложение, предоставляющее REST API

### Предварительные условия:

- Java 21
- PostgresSQL

# Запуск приложения с помощью Docker

Эта инструкция поможет вам запустить приложение в контейнере Docker.

## Предварительные требования

1. **Docker**: Убедитесь, что Docker установлен на вашем компьютере. Вы можете скачать и установить его
   с [официального сайта Docker](https://www.docker.com/get-started).

## Запуск или скачивание файла `docker-compose.yml` и `.env`

Чтобы запустить приложение с помощью docker-compose, необходимо клонировать проект к себе на компьютер, после чего
создать
в корне проекта файл `.env`, заполнить своими данными и указать с какой среды запускать `local` или `dev`:

[Генерация секретного JWT ключа](https://openreplay.com/tools/token-generator/): длина ключа `64`

<a href="materials/db_diagram.png">
    <img src="materials/TokenImage.png" alt="TokenImage" width="600"/>
</a>


```.dotenv
SPRING_PROFILES_ACTIVE="dev"

JWT_SECRET_KEY="Your secret key generate token"
JWT_SECRET_KEY_LOCAL="Your secret key generate token"

POSTGRES_DB="Your database"
POSTGRES_USER="Your username database"
POSTGRES_PASSWORD="Your password database"

PORT="8080"
POSTGRES_DB_URL="jdbc:postgresql://db-task-management-system/${POSTGRES_DB}"
POSTGRES_DB_USERNAME="${POSTGRES_USER}"
POSTGRES_DB_PASSWORD="${POSTGRES_PASSWORD}"

PORT_LOCAL="8181"
POSTGRES_DB_URL_LOCAL="jdbc:postgresql://db-task-management-system/${POSTGRES_DB}"
POSTGRES_DB_USERNAME_LOCAL=${POSTGRES_USER}"
POSTGRES_DB_PASSWORD_LOCAL="${POSTGRES_PASSWORD}"
```

Также для удобства вы можете скачать архив с файлами `.env` и `docker-compose.yml`, который содержит необходимые
настройки для запуска приложения.

[![Скачать docker-compose.yml](https://img.shields.io/badge/Скачать%20docker--compose.yml-blue)](https://drive.google.com/drive/folders/1ztmCCncx75RUAmWTNZv3hBcFH6u-fr1M?usp=drive_link)

## Шаги для запуска приложения

### 1. Запуск приложения с помощью Docker Compose

После вышеуказанных действий прописанных в инструкции, в директории, где находятся файлы прописать команду:

```bash
docker compose up
```

## Установка и настройка

### Пользователь-администратор

В процессе миграции базы данных был добавлен тестовый пользователь с правами администратора. Вы можете использовать
следующие учетные данные для входа в систему:

- **Логин:** `admin@gmail.com`
- **Пароль:** `admin`

### Как проверить

1. Убедитесь, что база данных была успешно мигрирована.
2. Запустите ваше приложение.
3. Перейдите на страницу входа и введите указанные учетные данные.

### Примечание

- Эта учетная запись предназначена только для тестирования. Рекомендуется изменить пароль и настройки учетной записи
  после первого входа в систему.

### После запуска приложения, можно запустить ```Swagger```

[![swagger](https://img.shields.io/badge/Открыть%20swagger-ui-green)](http://localhost:8080/swagger-ui/index.html)

### Мои запросы к приложению в Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/21948648/2sAYkDMLRS)

# Модель данных

## Диаграмма ER для модели данных

<a href="materials/db_diagram.png">
    <img src="materials/db_diagram.png" alt="db_diagram" width="600"/>
</a>

## RESTful API

**1. Описание API общих методов для аутентификации**

| METHOD | PATH          | DESCRIPTION              |
|--------|---------------|--------------------------|
| POST   | /auth/sign-up | Регистрация пользователя |
| POST   | /auth/sign-in | Авторизация пользователя |

###

**2. Описание API общих методов для управления задачами**

| METHOD | PATH                                      | DESCRIPTION                       |
|--------|-------------------------------------------|-----------------------------------|
| GET    | /api/v1/tasks                             | Получить все задачи               |
| GET    | /api/v1/tasks/{taskId}                    | Получить задачу по ее ID          |
| GET    | /api/v1/tasks/comments/by-taskId/{taskId} | Получить комментарии ее ID        |
| GET    | /api/v1/tasks/comments/author/{authorId}  | Получить комментарии по ID автора |
| GET    | /api/v1/tasks/assignee/{assigneeId}       | Получить задачи по ID исполнителя |

###

**3. Описание API общих методов для управления пользователями**

| METHOD | PATH                             | DESCRIPTION                                            |
|--------|----------------------------------|--------------------------------------------------------|
| POST   | /api/v1/user/comments/post       | Добавление комментарий к задаче по ID задачи           |
| PATCH  | /api/v1/user/comments/update     | Обновление содержимого комментария                     |
| PATCH  | /api/v1/user/tasks/update-status | Обновление задачи пользователя                         |
| GET    | /api/v1/show-user                | Получить список всех пользователей без администраторов |
| GET    | /api/v1/user/{userId}            | Получить информацию о пользователе по его ID           |

###

**4. Описание API общих методов для управления административными функциями**

| METHOD | PATH                               | DESCRIPTION                         |
|--------|------------------------------------|-------------------------------------|
| POST   | /api/v1/admin/tasks/create-task    | Создать новую задачу                |
| POST   | /api/v1/admin/comments/post        | Добавить комментарий к задаче по ID |
| PATCH  | /api/v1/admin/comments/update      | Обновление содержимого комментария  |
| PATCH  | /api/v1/admin/tasks/update         | Обновить задачу                     |
| PATCH  | /api/v1/admin/assignee-by-taskId   | Назначить исполнителя задачи по ID  |
| GET    | /api/v1/user                       | Получить список всех пользователей  |
| DELETE | /api/v1/admin/{taskId}             | Удаление задачи по ID               |
| DELETE | /api/v1/admin/comments/{commentId} | Удаление комментария по ID          |
