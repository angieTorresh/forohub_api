package alura_challenge.forohub_api.domain.usuario;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Usuario registrar(DatosRegistrarUsuario datos) {
        String contraseña = passwordEncoder.encode(datos.contraseña());
        datos = new DatosRegistrarUsuario(datos.nombre(), datos.nombreusuario(), datos.correo(), contraseña);
        Usuario usuario = new Usuario(datos);
        return usuarioRepository.save(usuario);
    }
}
