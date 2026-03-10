package com.aluracursos.challenge.forohub.g9.domain.topico;

import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosActualizacionTopico;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosRegistroTopico;
import jakarta.persistence.*;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Table(name="topicos")
@Entity(name="Topico")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Topico {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    String titulo;
    String mensaje;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaActualizacion;
    Boolean status;

    @Column(name = "autor_id")
    Long autor;

    String curso;
    //String respuestas;

    public Topico(DatosRegistroTopico datos) {
        this.id = null;
        this.status = datos.status() != null ? datos.status() : true;  // Prioriza DTO, default true
        this.titulo = datos.titulo();
        this.mensaje = datos.mensaje();
        this.fechaCreacion = datos.fechaCreacion() != null ? datos.fechaCreacion() : LocalDateTime.now();
        this.fechaActualizacion = datos.fechaActualizacion() == null ? datos.fechaActualizacion() : LocalDateTime.now();
        this.autor = datos.autor();
        this.curso = datos.curso();
        //this.respuestas = datos.respuestas();
    }

    public void actualizarDatosTopico(@Valid DatosActualizacionTopico datos) {
        if (datos.status() != null) {
            this.status = datos.status();
        }
        if (datos.titulo() != null) {
            this.titulo = datos.titulo();
        }
        if (datos.mensaje() != null) {
            this.mensaje = datos.mensaje();
        }

        this.fechaActualizacion = (datos.fechaActualizacion() != null) ? datos.fechaActualizacion() : LocalDateTime.now();

        if (datos.autor() != null) {
            this.autor = datos.autor();
        }
        if (datos.curso() != null) {
            this.curso = datos.curso();
        }
        /* TODO: cuando este lista la entidad Respuestas, implementar esa propiedad
        if (datos.respuestas() != null) {
            this.respuestas.actualizarRespuestas(datos.respuestas());
        }
        */
    }

    public void desactivar() {
        this.status = false;
    }
}
