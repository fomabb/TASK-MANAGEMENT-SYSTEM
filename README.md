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

1. **Docker**: Убедитесь, что Docker установлен на вашем компьютере. Вы можете скачать и установить его с [официального сайта Docker](https://www.docker.com/get-started).

## Запуск или скачивание файла `docker-compose.yml` и `.env`

Чтобы запустить приложение с помощью docker-compose, необходимо клонировать проект к себе на компьютер, после чего создать
в корне проекта файл `.env` после чего в нем прописать:

```.dotenv
JWT_SECRET_KEY="53A73E5F1C4E0A2D3B5F2D784E6A1B423D6F247D1F6E5C3A596D635A75327855"
JWT_EXPIRATION_TIME="60m"

POSTGRES_DB="Yor database"
POSTGRES_USER="Yor username database"
POSTGRES_PASSWORD="Yor password database"

PORT="8080"
POSTGRES_DB_URL="jdbc:postgresql://db-task-management-system/Yor database"
POSTGRES_DB_USERNAME="Yor username database"
POSTGRES_DB_PASSWORD="Yor password database"
```

Также для удобства вы можете скачать архив с файлами `.env` и `docker-compose.yml`, который содержит необходимые настройки 
для запуска приложения. 

[![Скачать docker-compose.yml](https://img.shields.io/badge/Скачать%20docker--compose.yml-blue)](https://drive.google.com/drive/folders/1ztmCCncx75RUAmWTNZv3hBcFH6u-fr1M?usp=drive_link)

## Шаги для запуска приложения

### 1. Запуск приложения с помощью Docker Compose

После вышеуказанных действий прописанных в инструкции, в директории где находятся файлы прописать команду:

```bash
docker compose up
```

### После запуска приложения, можно запустить ```Swagger```

[![swagger](https://img.shields.io/badge/Открыть%20swagger-ui-green)](http://localhost:8080/swagger-ui/index.html)

### Мои запросы к приложению в Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/21948648/2sAYkDMLRS)

# Data model

## ER diagram for the data model

<a href="materials/db_diagram.png">
    <img src="materials/db_diagram.png" alt="db_diagram" width="600"/>
</a>