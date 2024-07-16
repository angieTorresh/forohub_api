package alura_challenge.forohub_api.domain.topico;

import alura_challenge.forohub_api.domain.topico.validaciones.TopicoNoDuplicado;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private TopicoNoDuplicado topicoNoDuplicado;

    public DatosDetalleTopico registrar(DatosRegistrarTopico datos) throws ValidationException {
        topicoNoDuplicado.validar(datos.titulo(), datos.mensaje());
        Usuario topico = new Usuario(datos);
        topicoRepository.save(topico);
        return new DatosDetalleTopico(topico);
    }

}
