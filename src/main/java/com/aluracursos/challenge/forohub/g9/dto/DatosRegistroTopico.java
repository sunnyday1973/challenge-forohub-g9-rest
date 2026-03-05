package com.aluracursos.challenge.forohub.g9.dto;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosRegistroTopico(
        Long id,

        @NotBlank
        String titulo,

        @NotBlank
        String mensaje,

        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,

        Boolean status,

        @NotNull
        Long autor,

        @NotBlank
        String curso,

        String respuestas
) {
}
