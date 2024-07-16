package alura_challenge.forohub_api.domain.topico;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Null;

public record DatosRegistrarTopico(
        @NotNull
        String titulo,
        @NotNull
        String mensaje,
        @Null
        Long idAutor,
        @NotNull
        Long idCurso
) {
}
