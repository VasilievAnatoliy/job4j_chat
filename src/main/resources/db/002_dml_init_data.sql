insert into role (name)
values ('ROLE_USER');
insert into role (name)
values ('ROLE_ADMIN');

insert into person (login, password, role_id)values (
      'Vlad', '123',(select id from role where name = 'ROLE_ADMIN'));
insert into person (login, password, role_id)values (
      'Ivan', '123',(select id from role where name = 'ROLE_USER'));
insert into person (login, password, role_id)values (
      'Petr', '123',(select id from role where name = 'ROLE_USER'));


insert into room (name, person_id) values ('Sport', '1');
insert into room (name, person_id) values ('auto', '2');



insert into message (text, created, person_id, room_id)
            values ('Всем привет', '2022-01-24T09:30:33.177+00:00', '1', '1');
insert into message (text, created, person_id, room_id)
            values ('Привет', '2022-01-24T09:30:33.177+00:00', '2', '1');

insert into message (text, created, person_id, room_id)
            values ('Всем привет', '2022-01-24T09:30:33.177+00:00', '2', '2');
insert into message (text, created, person_id, room_id)
            values ('Привет', '2022-01-24T09:30:33.177+00:00', '3', '2');
