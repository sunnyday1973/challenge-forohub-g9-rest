create table usuarios(
    id bigint auto_increment primary key,
    login varchar(100) not null unique,
    password varchar(255)  not null
);