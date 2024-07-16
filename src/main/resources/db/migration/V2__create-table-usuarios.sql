
create table usuarios(

                        id bigint not null auto_increment,
                        nombre varchar(255) not null,
                        nombreusuario varchar(255) not null unique,
                        correo varchar(255) not null unique,
                        contraseña varchar(255) not null,
                        perfil varchar(100) not null,

                        primary key(id)
);