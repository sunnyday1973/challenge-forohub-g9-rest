package com.aluracursos.challenge.forohub.g9.infra.exceptions;

public record ErrorResponse(
        int status,
        String error,
        String message,
        String path
) {}