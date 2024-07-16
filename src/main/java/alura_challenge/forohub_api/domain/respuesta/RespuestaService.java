package alura_challenge.forohub_api.domain.respuesta;

import alura_challenge.forohub_api.domain.respuesta.validaciones.RespuestaNoDuplicada;
import jakarta.xml.bind.ValidationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@SuppressWarnings("all")
public class RespuestaService {
    @Autowired
    private RespuestaRepository respuestaRepository;

    @Autowired
    private RespuestaNoDuplicada respuestaNoDuplicada;

    public DatosDetalleRespuesta registrar(DatosRegistrarRespuesta datos) throws ValidationException {
        respuestaNoDuplicada.validar(datos.idTopico(), datos.mensaje());
        Respuesta respuesta = new Respuesta(datos);
        respuestaRepository.save(respuesta);
        return new DatosDetalleRespuesta(respuesta);
    }

}
