package com.aluracursos.challenge.forohub.g9.dto;

import com.aluracursos.challenge.forohub.g9.Topico;
import java.time.LocalDateTime;


public record DatosDetalleTopico(
        String titulo,
        String mensaje,
        LocalDateTime fechaCreacion,
        LocalDateTime fechaActualizacion,
        Boolean status,
        Long autor,
        String curso
        //String respuestas
) {
    public DatosDetalleTopico(Topico topico) {
        this(topico.getTitulo()
                , topico.getMensaje()
                , topico.getFechaCreacion()
                , topico.getFechaActualizacion()
                , topico.getStatus()
                , topico.getAutor()
                , topico.getCurso()
                //, topico.getRespuestas()
        );
    }
}
