package com.aluracursos.challenge.forohub.g9.domain.usuario.dto;

import com.aluracursos.challenge.forohub.g9.domain.usuario.Usuario;
import com.fasterxml.jackson.annotation.JsonAlias;
import java.time.LocalDateTime;


public record DatosDetalleUsuario(
        Long id,
        String nombres,
        String apellidos,
        @JsonAlias("login")
        String user,
        @JsonAlias("correo")
        String email,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        Boolean status,
        String password
) {
    public DatosDetalleUsuario(Usuario usuario) {
        this(usuario.getId()
                , usuario.getNombres()
                , usuario.getApellidos()
                , usuario.getUser()
                , usuario.getEmail()
                , usuario.getFechaCreacion()
                , usuario.getFechaActualizacion()
                , usuario.getStatus()
                , usuario.getPassword()
        );
    }
}
