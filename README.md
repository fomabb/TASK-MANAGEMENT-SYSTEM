# Task Management System

# RESTful Web-application is written using the following technologies:

- Maven
- Hibernate
- JPA
- PostgresSQL DB
- Spring Boot
- Spring security

### Short description of the project

- Standalone application providing REST API

### Prerequisites:

- Java 21
- PostgresSQL

# Запуск приложения с помощью Docker

Эта инструкция поможет вам запустить приложение в контейнере Docker.

## Предварительные требования

1. **Docker**: Убедитесь, что Docker установлен на вашем компьютере. Вы можете скачать и установить его с [официального сайта Docker](https://www.docker.com/get-started).

## Скачивание файла `docker-compose.yml`

Для удобства вы можете скачать необходимые файлы `.env` и `docker-compose.yml`, который содержит необходимые настройки для запуска приложения.

[![Скачать docker-compose.yml](https://img.shields.io/badge/Скачать%20docker--compose.yml-blue)](https://drive.google.com/drive/folders/1ztmCCncx75RUAmWTNZv3hBcFH6u-fr1M?usp=drive_link)

## Шаги для запуска приложения

### 1. Запуск приложения с помощью Docker Compose

После скачивания папки с файлами `.env` и `docker-compose.yml`, запустите приложение с помощью следующей команды cd путь к папке куда были скачаны вышеуказанные файлы.

```bash
docker compose up
```

### После запуска приложения, можно запустить ```Swagger```

[![swagger](https://img.shields.io/badge/Открыть%20swagger-ui-green)](http://localhost:8080/swagger-ui/index.html)

### Мои запросы к приложению в Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/21948648/2sAYkDMLRS)

# Data model

## ER diagram for the data model

![db_diagram.png](materials%2Fdb_diagram.png)