package alura_challenge.forohub_api.domain.topico;

import java.time.LocalDateTime;

public record DatosDetalleTopico(
        Long id,
        Long idAutor,
        Long idCurso,
        String titulo,
        String mensaje,
        Status status,
        LocalDateTime fecha,
        int respuestas
) {
    public DatosDetalleTopico (Usuario topico) {
        this(topico.getId(), topico.getAutor().getId(), topico.getCurso().getId(), topico.getTitulo(), topico.getMensaje(), topico.getStatus(), topico.getFechacreacion(), topico.getRespuestas());
    }
}
