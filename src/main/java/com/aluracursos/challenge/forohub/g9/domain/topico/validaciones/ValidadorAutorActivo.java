package com.aluracursos.challenge.forohub.g9.domain.topico.validaciones;

import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosRegistroTopico;
import org.springframework.stereotype.Component;

@Component
public class ValidadorAutorActivo implements ValidadorDeTopico {

    //@Autowired
   // private AutorRepository repositorio;

    public void validar(DatosRegistroTopico datos) {
        /*
        var autorEstaActivo = repositorio.findActivoById(datos.autor());
        if (!autorEstaActivo) {
            throw new ValidacionException("Autor no encontrado o inactivo.");
        }
        */
    }
}
