package alura_challenge.forohub_api.domain.topico.validaciones;

import alura_challenge.forohub_api.domain.topico.TopicoRepository;
import jakarta.validation.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TopicoNoDuplicado {
    @Autowired
    private TopicoRepository topicoRepository;

    public void validar(String titulo, String mensaje) {
        Boolean tituloDuplicado = topicoRepository.existsByTitulo(titulo);
        Boolean mensajeDuplicado = topicoRepository.existsByMensaje(mensaje);

        if (tituloDuplicado && mensajeDuplicado) {
            throw new ValidationException("Este tópico ya existe");
        }
    }

}
