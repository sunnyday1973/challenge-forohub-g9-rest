package com.aluracursos.challenge.forohub.g9.domain.usuario.dto;

import com.aluracursos.challenge.forohub.g9.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosListaUsuarios(
        Long id,
        String nombres,
        String apellidos,
        String email
) {
    public DatosListaUsuarios(Usuario usuario) {
        this(
                usuario.getId(),
                usuario.getNombres(),
                usuario.getApellidos(),
                usuario.getEmail()
        );
    }
}
