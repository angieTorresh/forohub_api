package alura_challenge.forohub_api.domain.topico;

public record DatosActualizarTopico(String titulo,
                                    String mensaje,
                                    Long idCurso,
                                    Status status) {
}
