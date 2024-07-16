package alura_challenge.forohub_api.domain.usuario;

import jakarta.validation.constraints.NotNull;

public record DatosRegistrarUsuario(@NotNull
                                    String nombre,
                                    @NotNull
                                    String nombreusuario,
                                    @NotNull
                                    String correo,
                                    @NotNull
                                    String contraseña) {
}
