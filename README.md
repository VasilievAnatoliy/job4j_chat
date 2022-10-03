## Чат на Rest API.

Приложение реализовывает чат с комнатами, с разной тематикой,
в которых происходит общение участников.

### Используемые технологии:
* Maven
* Spring Boot,
* Spring Data JPA,
* Spring Security(jwt)
* PostgreSQL,
* Liquibase,
* Checkstyle
___
### Для запуска приложения:
Необходимо: Java 17, Maven 3.8.4, PostgreSQL 14.  
В PostgreSQL создать базу данных chat (psql --username 'your username' 'your password'  create database chat;)  
Скачать файл, разархивировать zip файл, в application.properties(./chat-master/src/main/resources)
указать username и password от PostgreSQL.   

#### В проекте за счет Liquibase реализованно:   
 - создание таблиц для Б.Д.    
 - пользователь с правами ADMIN (login-Petr, password-123)  
 - первоначальная загрузка данных(2 пользователя, 2 комнаты и пару сообщений) если данные не нужны удаляем или 
комментируем загрузку данных из "db/003_dml_init_data.sql" в liquibase-changeLog.xml(./chat-master/src/main/resources)  

#### Запуск командой:   
mvn spring-boot:run  

---
http://localhost:8080/chat/person/sign-up - _Регистрация нового пользователя(ROLE_USER),
в body {"username": "userLogin", "password": "userPassword"}, password не менее 3 символов._  
http://localhost:8080/chat/login - _Получаем токен(в Headers)_  
Доступ http://localhost:8080/chat/  

_Пользователи:_  
PATCH person/ - _Изменение регистрационных данных пользователя в body {"username": "newLogin"} или
{"password": "newPassword"} или {"username": "newLogin", "password": "newPassword"}._  
_После изменения данных необходимо получить новый Токен._  

_только для роли ADMIN :_  
GET person/all - _Список всех пользователей._  
GET person/{id} - _Найти пользователя по id (где {id} идентификационный номер пользователя)._  
POST person/{id}/role - _Добавить роль пользователю ({id} - id пользователя), в body передаём("name": "ROLE_ADMIN")._  
DELETE person/{id}/role - _Удалить роль пользователю ({id} - id пользователя), в body передаём("name": "ROLE_ADMIN")._    

_Комнаты :_  
GET room/all - _Список всех комнат._  
GET room/{id} - _Найти комнату по id (где {id} номер комнаты)._  
POST room/ - _Создать комнату, в body передаём название комнаты("name": "название...")._  
DELETE room/{id} - _Удаление комнаты по id, удалить может только ADMIN._  

_Сообщения :_  
GET /message/{id} - _Найти сообщение по id._  
GET /message/room/{id} - _Список всех сообщений в комнате(указываем id комнаты в {id})._  
GET /message/person/{personId} - _Список всех сообщений пользователя(указываем id пользователя в {personId})._  
POST /message/room/{roomId} - _Создать сообщение в указанной комнате {roomId},
передаём в body ("text": "сообщение...")._   
PUT /message/{id} - _Редактирование текста сообщения, возможно только для автора сообщения,
в body ("text": "сообщение...").._   
DELETE /message/{id} - _Удаление сообщения по id, удалить может только автор сообщения или ADMIN._   






