
create table topicos(

                        id bigint not null auto_increment,
                        titulo varchar(255) not null unique,
                        mensaje text not null,
                        fechacreacion datetime not null,
                        status varchar(50) not null,
                        autor_id bigint not null,
                        curso_id bigint not null,
                        respuestas bigint not null,

                        primary key(id),
                        foreign key (autor_id) references usuarios(id),
                        foreign key (curso_id) references cursos(id)
);