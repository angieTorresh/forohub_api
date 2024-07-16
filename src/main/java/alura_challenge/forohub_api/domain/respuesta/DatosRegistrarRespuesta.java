package alura_challenge.forohub_api.domain.respuesta;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record DatosRegistrarRespuesta(
        @NotNull
        String mensaje,
        @NotNull
        Long idTopico,
        @Null
        Long idAutor,
        Boolean solucion) {
}
