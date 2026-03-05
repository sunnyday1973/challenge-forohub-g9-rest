create table topicos(
    id bigint auto_increment primary key,
    titulo varchar(100) not null unique,
    mensaje text not null,
    fecha_creacion  datetime default current_timestamp,
    status tinyint not null default 1,
    autor_id bigint not null,
    curso varchar(100)  not null
);