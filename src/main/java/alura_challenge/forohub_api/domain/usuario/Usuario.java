package alura_challenge.forohub_api.domain.usuario;

import alura_challenge.forohub_api.domain.perfil.Perfil;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "usuarios")
@Entity(name = "Usuario")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nombre;
    private String nombreusuario;
    private String correo;
    private String contraseña;
    @Enumerated(EnumType.STRING)
    private Perfil perfil;

    public Usuario(Long idUsuario) {
        this.id = idUsuario;
    }

    public Usuario(DatosRegistrarUsuario datos) {
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    @Override
    public String getPassword() {
        return contraseña;
    }

    @Override
    public String getUsername() {
        return nombreusuario;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void actualizarDatos(DatosActualizarUsuario datos) {
        if (datos.nombre() != null) {
            this.nombre = datos.nombre();
        }
        if (datos.nombreusuario() != null) {
            this.nombreusuario = datos.nombreusuario();
        }
        if (datos.correo() != null) {
            this.correo = datos.correo();
        }
        if (datos.contraseña() != null) {
            this.contraseña = datos.contraseña();
        }

        if (datos.perfil() != null) {
            this.perfil = datos.perfil();
        }
    }
}
