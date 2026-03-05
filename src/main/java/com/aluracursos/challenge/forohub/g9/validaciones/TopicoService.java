package com.aluracursos.challenge.forohub.g9.validaciones;

import com.aluracursos.challenge.forohub.g9.Topico;
import com.aluracursos.challenge.forohub.g9.TopicoRepository;
import com.aluracursos.challenge.forohub.g9.dto.DatosActualizacionTopico;
import com.aluracursos.challenge.forohub.g9.dto.DatosDetalleTopico;
import com.aluracursos.challenge.forohub.g9.dto.DatosRegistroTopico;
import com.aluracursos.challenge.forohub.g9.dto.DatosResultadoRegistroTopico;
import com.aluracursos.challenge.forohub.g9.validaciones.exepciones.ValidacionException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Optional;


@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidadorDeTopico> validadores;

    public DatosResultadoRegistroTopico crearTopico(DatosRegistroTopico datos) {
        // La API no debe permitir el registro de tópicos duplicados (con el mismo título y mensaje).
        if (topicoRepository.existsByTituloIgnoreCase(datos.titulo())) {
            throw new ValidacionException("Tópico duplicado: mismo título");
        }
/*
        if(datos.autor() != null && !tipicoRepository.existsById(datos.autor())) {
            throw new ValidacionException("No existe un autor con el id informado");
        }
*/
        // validaciones
        validadores.forEach(v -> v.validar(datos));

        var topico = topicoRepository.save(new Topico(datos));
        return new DatosResultadoRegistroTopico(topico);
    }

    public Topico actualizarDatosTopico(Long id, @Valid DatosActualizacionTopico datos) {
        // 1. Buscar el tópico por ID
        Optional<Topico> topicoExistente = topicoRepository.findById(id);
        if (!topicoExistente.isPresent()) {
            throw new ValidacionException("No existe un topico con el id informado");
        }

        // 2. Verificar si existe con isPresent()
        if(datos.autor() != null && !topicoRepository.existsByAutor(datos.autor())) {
            throw new ValidacionException("No existe un autor con el id informado");
        }

        // 3. Si existe, actualizarlo
        //topico = tipicoRepository.getReferenceById(optionalTopico.get().getId());
        var topico = topicoExistente.get();
        topico.actualizarDatosTopico(datos);
        System.out.println(topico.toString());
        return topico;
    }

    public void elimiarTopico(Long id) {
        Optional<Topico>  topicoExistente = topicoRepository.findById(id);
        if (!topicoExistente.isPresent()) {
            throw new ValidacionException("No existe un topico con el id informado");
        }
        topicoRepository.deleteById(id);
        //topicoExistente.desactivar();
    }
}