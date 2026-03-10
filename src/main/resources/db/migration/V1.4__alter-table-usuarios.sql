alter table usuarios add(
    apellidos varchar(150),
    nombres varchar(100),
    email varchar(50),
    fecha_creacion  datetime default current_timestamp,
    fecha_actualizacion  datetime,
    activo tinyint not null default 1
);