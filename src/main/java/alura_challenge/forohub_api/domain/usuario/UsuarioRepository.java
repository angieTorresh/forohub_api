package alura_challenge.forohub_api.domain.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Usuario findByNombreusuario(String nombreusuario);

    @Query("select exists(select 1 from Usuario u where u.id = :usuarioAutenticadoId and u.perfil = 'ADMINISTRADOR')")
    boolean esAdministrador(Long usuarioAutenticadoId);
}
