create table if not exists role (
    id serial primary key,
    name        VARCHAR(50) not null unique
);

create table if not exists person (
    id serial primary key,
    login       varchar(50) not null unique,
    password    varchar(50) not null,
    role_id   int not null references role(id)
);

create table if not exists room (
    id serial primary key,
    name        varchar(200),
    person_id int not null references person(id)
);

create table if not exists message (
    id serial primary key,
    text        varchar(2000),
    created      timestamp,
    person_id int not null references person(id),
    room_id   int not null references room(id)
);

