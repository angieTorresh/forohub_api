package alura_challenge.forohub_api.controller;

import alura_challenge.forohub_api.domain.topico.*;
import alura_challenge.forohub_api.domain.usuario.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.nio.file.AccessDeniedException;
import java.util.Optional;

@RestController
@RequestMapping("/topicos")
@Tag(name = "Topicos", description = "Operaciones relacionadas con los topicos del foro.")
public class TopicoController {

    @Autowired
    TopicoService topicoService;
    @Autowired
    private TopicoRepository topicoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Crea un nuevo topico en el foro.")
    public ResponseEntity<DatosDetalleTopico> registrarTopico(@RequestBody @Valid DatosRegistrarTopico datos) throws ValidationException {
        alura_challenge.forohub_api.domain.usuario.Usuario usuarioAutenticado = (alura_challenge.forohub_api.domain.usuario.Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idAutor = usuarioAutenticado.getId();
        var response = topicoService.registrar(new DatosRegistrarTopico(datos.titulo(), datos.mensaje(), idAutor, datos.idCurso()));
        return ResponseEntity.ok(response);
    }

    @GetMapping
    @Transactional
    @Operation(summary = "Obtiene el listado de topicos del foro.")
    public ResponseEntity<Page<DatosDetalleTopico>> listarTopicos(@PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByStatusAbierto(paginacion).map(DatosDetalleTopico::new));
    }

    @GetMapping("/{cursoId}")
    @Transactional
    @Operation(summary = "Obtiene el detalle de un tópico en específico pasando su id.")
    public ResponseEntity<DatosDetalleTopico> detallarTopico(@PathVariable Long cursoId) throws EntityNotFoundException{
        Optional<Usuario> topico =  topicoRepository.findById(cursoId);

        if (topico.isPresent()) {
            return ResponseEntity.ok(new DatosDetalleTopico(topico.get()));
        }
        throw new EntityNotFoundException("El tópico indicado no se encuentra en la base de datos.");
    }

    @GetMapping("/curso={cursoId}")
    @Transactional
    @Operation(summary = "Obtiene el listado de topicos del foro según el id del curso.")
    public ResponseEntity<Page<DatosDetalleTopico>> listarTopicosPorCurso(@PathVariable Long cursoId, @PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(topicoRepository.findByCursoId(cursoId, paginacion).map(DatosDetalleTopico::new));
    }

    @PutMapping("/{cursoId}")
    @Transactional
    @Operation(summary = "Actualiza un tópico en específico pasando su id.")
    public ResponseEntity<DatosDetalleTopico> actualizarTopico(@PathVariable Long cursoId, @RequestBody @Valid DatosActualizarTopico datos) throws EntityNotFoundException, AccessDeniedException {
        Long usuarioAutenticadoId = ((alura_challenge.forohub_api.domain.usuario.Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Optional<Usuario> topicoOptional =  topicoRepository.findById(cursoId);

        if (topicoOptional.isPresent()) {
            Usuario topico = topicoOptional.get();
            if (usuarioAutenticadoId.equals(topico.getAutor().getId()) || usuarioRepository.esAdministrador(usuarioAutenticadoId)) {
                topico.actualizarDatos(datos);
                topicoRepository.save(topico);
                return ResponseEntity.ok(new DatosDetalleTopico(topico));
            } else {
                throw new AccessDeniedException("No tiene permiso para actualizar este tópico.");
            }
        }
        throw new EntityNotFoundException("El tópico indicado no se encuentra en la base de datos.");
    }

    @DeleteMapping("/{cursoId}")
    @Transactional
    @Operation(summary = "Elimina un tópico en específico pasando su id.")
    public ResponseEntity eliminarTopico(@PathVariable Long cursoId) throws EntityNotFoundException, AccessDeniedException {
        Long usuarioAutenticadoId = ((alura_challenge.forohub_api.domain.usuario.Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Optional<Usuario> topicoOptional = topicoRepository.findById(cursoId);

        if (topicoOptional.isPresent()) {
            Usuario topico = topicoOptional.get();
            if (usuarioAutenticadoId.equals(topico.getAutor().getId()) || usuarioRepository.esAdministrador(usuarioAutenticadoId)) {
                topicoRepository.delete(topico);
                return ResponseEntity.ok("El tópico ha sido eliminado correctamente.");
            } else {
                throw new AccessDeniedException("No tiene permiso para eliminar este tópico.");
            }
        }
        throw new EntityNotFoundException("El tópico indicado no se encuentra en la base de datos.");
    }
}
