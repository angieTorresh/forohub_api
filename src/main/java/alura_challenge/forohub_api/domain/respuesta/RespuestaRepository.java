package alura_challenge.forohub_api.domain.respuesta;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RespuestaRepository extends JpaRepository<Respuesta, Long> {
    Boolean existsByTopicoId(Long idTopico);

    Boolean existsByMensaje(String mensaje);

    Page<Respuesta> findByTopicoId(Long idTopico, Pageable paginacion);
}
