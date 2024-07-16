package alura_challenge.forohub_api.domain.topico;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface TopicoRepository extends JpaRepository<Usuario, Long> {
    Boolean existsByTitulo(String titulo);

    Boolean existsByMensaje(String mensaje);

    @Query("""
            select t from Topico t
            where t.status = 'ABIERTO'
            order by t.fechacreacion asc
            """)
    Page<Usuario> findByStatusAbierto(Pageable paginacion);

    Page<Usuario> findByCursoId(Long cursoId, Pageable paginacion);
}
