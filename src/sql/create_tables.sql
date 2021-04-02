drop table if exists usuario;
create table usuario(
	id serial primary key,
	nombre varchar(100),
	clave varchar(100)
);