package alura_challenge.forohub_api.controller;

import alura_challenge.forohub_api.domain.usuario.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuarios", description = "Operaciones relacionadas con los usuarios del foro.")
public class UsuarioController {

    @Autowired
    UsuarioService usuarioService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping
    @Transactional
    @Operation(summary = "Crea un nuevo usuario en el foro.")
    public ResponseEntity<alura_challenge.forohub_api.domain.usuario.Usuario> registrarUsuario(@RequestBody @Valid DatosRegistrarUsuario datos) {
        return ResponseEntity.ok(usuarioService.registrar(datos));
    }

    @GetMapping
    @Transactional
    @Operation(summary = "Obtiene el listado de usuarios del foro.")
    public ResponseEntity<Page<DatosDetalleUsuario>> listarUsuarios(@PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(usuarioRepository.findAll(paginacion).map(DatosDetalleUsuario::new));
    }

    @GetMapping("/{usuarioId}")
    @Transactional
    @Operation(summary = "Obtiene la información de un usuario del foro pasando su id.")
    public ResponseEntity<DatosDetalleUsuario> mostrarUsuario(@PathVariable Long usuarioId) throws EntityNotFoundException {
        Optional<alura_challenge.forohub_api.domain.usuario.Usuario> usuario =  usuarioRepository.findById(usuarioId);

        if (usuario.isPresent()) {
            return ResponseEntity.ok(new DatosDetalleUsuario(usuario.get()));
        }
        throw new EntityNotFoundException("El usuario indicado no se encuentra en la base de datos.");
    }


    @PutMapping("/{usuarioId}")
    @Transactional
    @Operation(summary = "Actualiza la infromación de un usuario en específico pasando su id.")
    public ResponseEntity<?> actualizarUsuario(@PathVariable Long usuarioId, @RequestBody @Valid DatosActualizarUsuario datos) throws EntityNotFoundException, AccessDeniedException {
        Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuarioAActualizar = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario indicado no se encuentra en la base de datos."));

        if (!usuarioAutenticado.getId().equals(usuarioAActualizar.getId()) && !usuarioRepository.esAdministrador(usuarioAutenticado.getId())) {
            throw new AccessDeniedException("No tiene permiso para actualizar este usuario.");
        }

        if (datos.perfil() != null && !usuarioRepository.esAdministrador(usuarioAutenticado.getId())) {
            throw new AccessDeniedException("Solo un administrador puede cambiar el perfil del usuario.");
        }

        usuarioAActualizar.actualizarDatos(datos);

        if (datos.contraseña() != null && !datos.contraseña().isBlank()) {
            usuarioAActualizar.setContraseña(passwordEncoder.encode(datos.contraseña()));
        }

        usuarioRepository.save(usuarioAActualizar);

        return ResponseEntity.ok("Usuario actualizado correctamente.");
    }

    @DeleteMapping("/{usuarioId}")
    @Transactional
    @Operation(summary = "Elimina un usuario del foro.")
    public ResponseEntity<?> eliminarUsuario(@PathVariable Long usuarioId) throws EntityNotFoundException, AccessDeniedException {
        Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Usuario usuarioAEliminar = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new EntityNotFoundException("El usuario indicado no se encuentra en la base de datos."));

        if (!usuarioAutenticado.getId().equals(usuarioAEliminar.getId()) && !usuarioRepository.esAdministrador(usuarioAutenticado.getId())) {
            throw new AccessDeniedException("No tiene permiso para eliminar este usuario.");
        }

        usuarioRepository.delete(usuarioAEliminar);

        return ResponseEntity.ok("Usuario eliminado correctamente.");
    }

}
