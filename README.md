## Чат на Rest API.

Приложение реализовывает чат с комнатами, с разной тематикой,
в которых происходит общение участников.

### Используемые технологии:
* Maven
* Spring Boot,
* Spring Data JPA,
* PostgreSQL,
* Liquibase,
* Checkstyle
___
### Для запуска приложения:
Необходимо: Java 17, Maven 3.8.4, PostgreSQL 14.  
В PostgreSQL создать базу данных chat (psql --username 'your username' 'your password'  create database chat;)  
Скачать файл, разархивировать zip файл, в application.properties(./chat-master/src/main/resources)
указать username и password от PostgreSQL.   

В проекте за счет Liquibase реализованно создание таблиц и первоначальная загрузка 
данных(3 пользователя, 2 комнаты и пару сообщений) если данные не нужны удаляем или комментируем
загрузку данных из "db/002_dml_init_data.sql" в liquibase-changeLog.xml(./chat-master/src/main/resources)  

_Запуск командой:_  
mvn spring-boot:run
---
Доступ http://localhost:8080/chat/  
#### Команды:  

_Пользователи:_  
GET person/ - _Список всех пользователей._  
GET person/{id} - _Найти пользователя по id (где {id} идентификационный номер пользователя)._  
POST person/ - _Создать пользователя._
DELETE person/{id} - _Удаление пользователя по id._

_Комнаты:_  
GET room/ - _Список всех комнат._  
GET room/{id} - _Найти комнату по id (где {id} номер комнаты)._  
POST room/ - _Создать комнату в параметрах personId id (room/?personId=1),
в body передаём название комнаты("name": "название...")._  
DELETE room/{id} - _Удаление комнаты по id._  

_Сообщения:_
GET /message/{id} - _Найти сообщение по id._  
GET /message/room/{id} - _Список всех сообщений в комнате(указываем id комнаты в {id})._  
GET /message/person/{personId} - _Список всех сообщений пользователя(указываем id пользователя в {personId})._  
POST /message/room/{roomId} - _Создать сообщение в указанной комнате {roomId}, в параметрах передаём "personId" id,
в body ("text": "сообщение...")._  
DELETE /message/{id} - _Удаление сообщения по id._





