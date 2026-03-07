package com.aluracursos.challenge.forohub.g9.domain.usuario;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public record UsernamePasswordAuthenticationToken(
        @JsonAlias("login")
        String user,
        String password
    ) {
}
