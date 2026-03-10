package com.aluracursos.challenge.forohub.g9.domain.usuario.validaciones;

import com.aluracursos.challenge.forohub.g9.domain.usuario.UsuarioRepository;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosRegistroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class ValidadorUsuarioActivo implements ValidadorDeNuevoUsuario {

    //@Autowired
    //Sprivate UsuarioRepository repositorio;

    @Override
    public void validar(DatosRegistroUsuario datos) {
        /*
        var autorEstaActivo = repositorio.findStatusById(datos.autor());
        if (!autorEstaActivo) {
            throw new ValidacionException("Autor no encontrado o inactivo.");
        }
        */
    }
}
