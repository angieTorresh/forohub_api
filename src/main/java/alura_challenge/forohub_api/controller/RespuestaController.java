package alura_challenge.forohub_api.controller;

import alura_challenge.forohub_api.domain.respuesta.*;
import alura_challenge.forohub_api.domain.usuario.Usuario;
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
@RequestMapping("/respuestas")
@Tag(name = "Respuestas", description = "Operaciones relacionadas con las respuestas del foro.")
public class RespuestaController {

    @Autowired
    RespuestaService respuestaService;
    @Autowired
    private RespuestaRepository respuestaRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping
    @Transactional
    @Operation(summary = "Crea una nueva respuesta a un tópico.")
    public ResponseEntity<DatosDetalleRespuesta> registrarRespuesta(@RequestBody @Valid DatosRegistrarRespuesta datos) throws ValidationException {
        Usuario usuarioAutenticado = (Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long idAutor = usuarioAutenticado.getId();
        var response = respuestaService.registrar(new DatosRegistrarRespuesta(datos.mensaje(), datos.idTopico(), idAutor, datos.solucion()));
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{topicoId}")
    @Transactional
    @Operation(summary = "Obtiene las respuestas de un tópico específico.")
    public ResponseEntity<Page<DatosDetalleRespuesta>> listarRespuestasPorTopico(@PathVariable Long topicoId, @PageableDefault(size = 5) Pageable paginacion) {
        return ResponseEntity.ok(respuestaRepository.findByTopicoId(topicoId, paginacion).map(DatosDetalleRespuesta::new));
    }

    @GetMapping("/{topicoId}/{respuestaId}")
    @Transactional
    @Operation(summary = "Obtiene una respuesta específica de un tópico.")
    public ResponseEntity<DatosDetalleRespuesta> detallarRespuesta(@PathVariable Long topicoId, @PathVariable Long respuestaId) throws EntityNotFoundException {
        Optional<Respuesta> respuesta = respuestaRepository.findById(respuestaId);

        if (respuesta.isPresent() && respuesta.get().getTopico().getId().equals(topicoId)){
            return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta.get()));
        }
        throw new EntityNotFoundException("La respuesta al tópico indicado no se encuentra en la base de datos.");
    }

    @PutMapping("/{respuestaId}")
    @Transactional
    @Operation(summary = "Actualiza una respuesta en específico pasando su id.")
    public ResponseEntity<DatosDetalleRespuesta> actualizarRespuesta(@PathVariable Long respuestaId, @RequestBody @Valid DatosActualizarRespuesta datos) throws EntityNotFoundException, AccessDeniedException {
        Long usuarioAutenticadoId = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(respuestaId);

        if (respuestaOptional.isPresent()) {
            Respuesta respuesta = respuestaOptional.get();
            if (usuarioAutenticadoId.equals(respuesta.getAutor().getId()) || usuarioRepository.esAdministrador(usuarioAutenticadoId)) {
                respuesta.actualizarDatos(datos);
                respuestaRepository.save(respuesta);
                return ResponseEntity.ok(new DatosDetalleRespuesta(respuesta));
            } else {
                throw new AccessDeniedException("No tiene permiso para actualizar esta respuesta.");
            }
        }
        throw new EntityNotFoundException("La respuesta indicada no se encuentra en la base de datos.");
    }



    @DeleteMapping("/{respuestaId}")
    @Transactional
    @Operation(summary = "Elimina una respuesta en específico pasando su id.")
    public ResponseEntity eliminarRespuesta(@PathVariable Long respuestaId) throws EntityNotFoundException, AccessDeniedException {
        Long usuarioAutenticadoId = ((Usuario) SecurityContextHolder.getContext().getAuthentication().getPrincipal()).getId();
        Optional<Respuesta> respuestaOptional = respuestaRepository.findById(respuestaId);

        if (respuestaOptional.isPresent()) {
            Respuesta respuesta = respuestaOptional.get();
            if (usuarioAutenticadoId.equals(respuesta.getAutor().getId()) || usuarioRepository.esAdministrador(usuarioAutenticadoId)) {
                respuestaRepository.delete(respuesta);
                return ResponseEntity.noContent().build();
            } else {
                throw new AccessDeniedException("No tiene permiso para eliminar esta respuesta.");
            }
        }
        throw new EntityNotFoundException("La respuesta indicada no se encuentra en la base de datos.");
    }
}
