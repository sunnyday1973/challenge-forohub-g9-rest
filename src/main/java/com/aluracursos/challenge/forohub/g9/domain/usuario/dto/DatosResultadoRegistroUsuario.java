package com.aluracursos.challenge.forohub.g9.domain.usuario.dto;

import com.aluracursos.challenge.forohub.g9.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosResultadoRegistroUsuario (
        Long id,
        String nombres,
        String apellidos,
        @JsonAlias("correo")
        String email,
        @JsonAlias("login")
        String user,
        String password
) {
    public DatosResultadoRegistroUsuario(Usuario usuario) {
        this(usuario.getId()
                , usuario.getApellidos()
                , usuario.getNombres()
                , usuario.getEmail()
                , usuario.getUser()
                , usuario.getPassword()
        );
    }
}
