package com.aluracursos.challenge.forohub.g9.domain.topico.validaciones;

import com.aluracursos.challenge.forohub.g9.domain.topico.Topico;
import com.aluracursos.challenge.forohub.g9.domain.topico.TopicoRepository;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosActualizacionTopico;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosListaTopicos;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosRegistroTopico;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosResultadoRegistroTopico;
import com.aluracursos.challenge.forohub.g9.domain.exepciones.ValidacionException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class TopicoService {
    @Autowired
    private TopicoRepository topicoRepository;

    @Autowired
    private List<ValidadorDeTopico> validadores;

    public Page<DatosListaTopicos> listar(Pageable paginacion, String curso, Integer anio) {
        Page<DatosListaTopicos> pagina;
        if (curso != null && anio != null) {
            // Ambos criterios
            pagina = topicoRepository
                    .findAllByStatusTrueAndCursoContainingIgnoreCaseAndFechaCreacionYear(curso, anio, paginacion)
                    .map(DatosListaTopicos::new);
        } else if (curso != null) {
            // Solo curso
            pagina = topicoRepository
                    .findAllByStatusTrueAndCursoContainingIgnoreCase(curso, paginacion)
                    .map(DatosListaTopicos::new);
        } else if (anio != null) {
            // Solo año
            pagina = topicoRepository
                    .findAllByStatusTrueAndFechaCreacionYear(anio, paginacion)
                    .map(DatosListaTopicos::new);
        } else {
            // Todos
            pagina = topicoRepository
                    .findAllByStatusTrue(paginacion)
                    .map(DatosListaTopicos::new);
        }

        return pagina;
    }

    public Topico detallar(Long id) {
        return topicoRepository.getReferenceById(id);
    }

    public DatosResultadoRegistroTopico crear(DatosRegistroTopico datos) {
        // La API no debe permitir el registro de tópicos duplicados con el mismo título
        if (topicoRepository.existsByTituloIgnoreCase(datos.titulo())) {
            throw new ValidacionException("Tópico duplicado: mismo título");
        }
        /* TODO: cuanto este Usuario, descomentar esa validacion
                if(datos.autor() != null && !tipicoRepository.existsById(datos.autor())) {
                    throw new ValidacionException("No existe un autor con el id informado");
                }
        */
        // validaciones
        validadores.forEach(v -> v.validar(datos));

        var topico = topicoRepository.save(new Topico(datos));
        return new DatosResultadoRegistroTopico(topico);
    }

    public Topico actualizar(Long id, @Valid DatosActualizacionTopico datos) {
        // 1. Buscar el tópico por ID
        Optional<Topico> topicoExistente = topicoRepository.findById(id);

        // 2. Verificar si existe con isPresent()
        if (!topicoExistente.isPresent()) {
            throw new ValidacionException("No existe un topico con el id informado");
        }

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

    public void elimiar(Long id) {
        Optional<Topico>  topicoExistente = topicoRepository.findById(id);
        if (!topicoExistente.isPresent()) {
            throw new ValidacionException("No existe un topico con el id informado");
        }
        topicoRepository.deleteById(id);
        //topicoExistente.desactivar();
    }
}