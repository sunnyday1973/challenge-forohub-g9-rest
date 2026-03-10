package com.aluracursos.challenge.forohub.g9.domain.usuario.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosActualizacionUsuario(
        @NotBlank
        String nombres,
        @NotBlank
        String apellidos,
        @JsonAlias("correo") @Email
        String email,
        LocalDateTime fechaActualizacion,
        String password
) {
}
