
create table respuestas(

                        id bigint not null auto_increment,
                        mensaje text not null,
                        topico_id bigint not null,
                        fechacreacion datetime not null,
                        autor_id bigint not null,
                        solucion boolean not null,

                        primary key(id),
                        foreign key (topico_id) references topicos(id),
                        foreign key (autor_id) references usuarios(id)
);