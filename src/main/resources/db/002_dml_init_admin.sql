insert into role (name) values ('ROLE_ADMIN');
insert into role (name) values ('ROLE_USER');

insert into person (username, password) values ('Petr', '$2a$10$1qi2PCZ0xvwb7vLmp4DKSuFthFPb1jFproLs84jNZyaJeiummseiS');

insert into persons_roles(person_id, role_id) values (1, 1);
insert into persons_roles(person_id, role_id) values (1, 2);