package com.aluracursos.challenge.forohub.g9.domain.usuario.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRegistroUsuario(
        @NotBlank
        String nombres,
        @NotBlank
        String apellidos,
        @JsonAlias("correo") @NotBlank @Email
        String email,
        @JsonAlias("login") @NotBlank
        String user,
        @NotBlank
        String password
    ) {
}
