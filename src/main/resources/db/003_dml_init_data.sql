

insert into person (username, password)values (
       'Vlad', '$2a$10$1qi2PCZ0xvwb7vLmp4DKSuFthFPb1jFproLs84jNZyaJeiummseiS');
insert into person (username, password)values (
       'Ivan', '$2a$10$1qi2PCZ0xvwb7vLmp4DKSuFthFPb1jFproLs84jNZyaJeiummseiS');

insert into persons_roles(person_id, role_id) values (2, (
    select id from role where name = 'ROLE_USER'));
insert into persons_roles(person_id, role_id) values (3, (
    select id from role where name = 'ROLE_USER'));

insert into room (name, person_id) values ('Sport', '2');
insert into room (name, person_id) values ('auto', '3');


insert into message (text, created, person_id, room_id)
values ('Всем привет', '2022-01-24T09:30:33.177+00:00', '2', '1');
insert into message (text, created, person_id, room_id)
values ('Привет', '2022-01-24T09:30:33.177+00:00', '1', '1');

insert into message (text, created, person_id, room_id)
values ('Всем привет', '2022-01-24T09:30:33.177+00:00', '2', '2');
insert into message (text, created, person_id, room_id)
values ('Привет', '2022-01-24T09:30:33.177+00:00', '3', '2');