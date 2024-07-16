package alura_challenge.forohub_api.domain.respuesta;

import alura_challenge.forohub_api.domain.topico.Usuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name = "respuestas")
@Entity(name = "Respuesta")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Respuesta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String mensaje;
    private LocalDateTime fechacreacion;
    private Boolean solucion;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "topico_id")
    private Usuario topico;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id")
    private alura_challenge.forohub_api.domain.usuario.Usuario autor;

    public Respuesta(DatosRegistrarRespuesta datos) {
        this.mensaje = datos.mensaje();
        this.topico = new Usuario(datos.idTopico());
        this.autor = new alura_challenge.forohub_api.domain.usuario.Usuario(datos.idAutor());
        this.fechacreacion = LocalDateTime.now();
        if (datos.solucion() != null) {
            this.solucion = datos.solucion();
        } else {
            this.solucion = false;
        }
    }

    public void actualizarDatos(DatosActualizarRespuesta datos) {
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }
        if (datos.idTopico() != null) {
            this.topico = new Usuario(datos.idTopico());
        }
        if (datos.solucion() != null) {
            this.solucion = datos.solucion();
        }
    }
}
