package com.aluracursos.challenge.forohub.g9.dto;

import com.aluracursos.challenge.forohub.g9.Topico;

import java.time.LocalDateTime;


public record DatosResultadoRegistroTopico(
        Long id,
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        Boolean status,
        Long autor,
        String curso
        //String respuestas
) {
    public DatosResultadoRegistroTopico(Topico topico) {
        this(topico.getId()
                , topico.getTitulo()
                , topico.getMensaje()
                , topico.getFechaCreacion()
                , topico.getStatus()
                , topico.getAutor()
                , topico.getCurso()
                //, topico.getRespuestas()
        );
    }
}
