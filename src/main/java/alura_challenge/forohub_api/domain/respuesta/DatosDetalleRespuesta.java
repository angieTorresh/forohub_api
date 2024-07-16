package alura_challenge.forohub_api.domain.respuesta;

import java.time.LocalDateTime;

public record DatosDetalleRespuesta(Long id,
                                    Long idAutor,
                                    Long idTopico,
                                    String mensaje,
                                    LocalDateTime fecha) {
    public DatosDetalleRespuesta(Respuesta respuesta) {
        this(respuesta.getId(), respuesta.getAutor().getId(), respuesta.getTopico().getId(), respuesta.getMensaje(), respuesta.getFechacreacion());
    }
}
