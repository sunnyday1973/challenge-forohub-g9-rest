package com.aluracursos.challenge.forohub.g9.validaciones;

import com.aluracursos.challenge.forohub.g9.dto.DatosRegistroTopico;
import org.springframework.beans.factory.annotation.Autowired;
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
