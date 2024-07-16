package alura_challenge.forohub_api.domain.usuario;

import alura_challenge.forohub_api.domain.perfil.Perfil;

public record DatosActualizarUsuario(String nombre,
                                     String nombreusuario,
                                     String correo,
                                     String contraseña,
                                     Perfil perfil) {
}
