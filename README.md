# Система управления задачами

# Полноценное веб-приложение REST написано с использованием следующих технологий:

- Maven
- Hibernate
- JPA
- PostgresSQL DB
- Spring Boot
- Spring security

### Краткое описание проекта

- Автономное приложение, предоставляющее REST API

### Предварительные условия:

- Java 21
- PostgresSQL

# Запуск приложения с помощью Docker

Эта инструкция поможет вам запустить приложение в контейнере Docker.

## Предварительные требования

1. **Docker**: Убедитесь, что Docker установлен на вашем компьютере. Вы можете скачать и установить его с [официального сайта Docker](https://www.docker.com/get-started).

## Скачивание файла `docker-compose.yml`

Для удобства вы можете клонировать проект и скачать файл `.env`, который содержит необходимые настройки для запуска приложения. Нужно его разместить в корне проекта. После этого вы сможете запустить приложение. Также вы можете скачать архив целиком, где расположены файлы `.env` и `docker-compose.yml`, распаковать у себя на компьютере.

[![Скачать docker-compose.yml](https://img.shields.io/badge/Скачать%20docker--compose.yml-blue)](https://drive.google.com/drive/folders/1ztmCCncx75RUAmWTNZv3hBcFH6u-fr1M?usp=drive_link)

## Шаги для запуска приложения

### 1. Запуск приложения с помощью Docker Compose

После клонирования проекта или распаковки архива, запустите приложение с помощью следующей команды:

```bash
docker compose up
```

### После запуска приложения, можно запустить ```Swagger```

[![swagger](https://img.shields.io/badge/Открыть%20swagger-ui-green)](http://localhost:8080/swagger-ui/index.html)

### Мои запросы к приложению в Postman

[![Run in Postman](https://run.pstmn.io/button.svg)](https://documenter.getpostman.com/view/21948648/2sAYkDMLRS)
