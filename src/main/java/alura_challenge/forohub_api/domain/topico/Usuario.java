package alura_challenge.forohub_api.domain.topico;

import alura_challenge.forohub_api.domain.curso.Curso;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Table(name = "topicos")
@Entity(name = "Topico")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String titulo;
    private String mensaje;
    private LocalDateTime fechacreacion;
    @Enumerated(EnumType.STRING)
    private Status status;
    private int respuestas;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private alura_challenge.forohub_api.domain.usuario.Usuario autor;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "curso_id")
    private Curso curso;

    public Usuario(DatosRegistrarTopico datos) {
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechacreacion = LocalDateTime.now();
        this.status = Status.ABIERTO;
        this.respuestas = 0;
        this.autor = new alura_challenge.forohub_api.domain.usuario.Usuario(datos.idAutor());
        this.curso = new Curso(datos.idCurso());
    }

    public Usuario(Long idTopico) {
        this.id = idTopico;
    }

    public void setCurso(Long idCurso) {
        this.curso = new Curso(idCurso);
    }

    public void actualizarDatos(DatosActualizarTopico datos) {
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.idCurso() != null) {
            this.curso = new Curso(datos.idCurso());
        }
        if (datos.status() != null) {
            this.status = datos.status();
        }
    }
}
