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

## Скачивание файла `docker-compose.yml`

Чтобы запустить приложение с помощью docker-compose, необходимо клонировать проект, после чего скачать файл `.env` нажав 
на кнопку ниже, после чего поместить скаченный файл в корень проекта. Также для удобства вы можете скачать архив с 
файлами `.env` и `docker-compose.yml`, который содержит необходимые настройки для запуска приложения. 

[![Скачать docker-compose.yml](https://img.shields.io/badge/Скачать%20docker--compose.yml-blue)](https://drive.google.com/drive/folders/1ztmCCncx75RUAmWTNZv3hBcFH6u-fr1M?usp=drive_link)

## Шаги для запуска приложения

### 1. Запуск приложения с помощью Docker Compose

После вышеуказанных действий, указанных в инструкции, в командной строке необходимо попасть в директорию, где находятся
вышеуказанные файлы и прописать:

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