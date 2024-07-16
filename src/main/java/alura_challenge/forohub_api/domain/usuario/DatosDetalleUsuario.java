package alura_challenge.forohub_api.domain.usuario;

import alura_challenge.forohub_api.domain.perfil.Perfil;

public record DatosDetalleUsuario(Long id,
                                  String nombre,
                                  String nombreusuario,
                                  Perfil perfil) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(usuario.getId(), usuario.getNombre(), usuario.getNombreusuario(), usuario.getPerfil());
    }
}
