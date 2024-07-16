package alura_challenge.forohub_api.domain.respuesta.validaciones;

import alura_challenge.forohub_api.domain.respuesta.RespuestaRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RespuestaNoDuplicada {
    @Autowired
    private RespuestaRepository respuestaRepository;

    public void validar(Long idTopico, String mensaje) {
        Boolean topicoDuplicado = respuestaRepository.existsByTopicoId(idTopico);
        Boolean mensajeDuplicado = respuestaRepository.existsByMensaje(mensaje);

        if (topicoDuplicado && mensajeDuplicado) {
            throw new ValidationException("Esta respuesta ya existe en el tópico");
        }
    }

}
