package com.aluracursos.challenge.forohub.g9.domain.controller;

import com.aluracursos.challenge.forohub.g9.domain.topico.DatosListaTopicosModelAssembler;
import com.aluracursos.challenge.forohub.g9.domain.topico.Topico;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosActualizacionTopico;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosDetalleTopico;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosListaTopicos;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosRegistroTopico;
import com.aluracursos.challenge.forohub.g9.domain.topico.validaciones.TopicoService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.hateoas.EntityModel;
import org.springframework.data.web.SortDefault;


@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/topicos")
public class TopicosController {

    @Autowired
    private TopicoService topicoService;

    // PagedResourcesAssembler se usa para convertir una Page en un PagedModel.
    @Autowired
    private PagedResourcesAssembler<DatosListaTopicos> pagedResourcesAssembler;

    // Inyectamos nuestro DatosListaTopicosModelAssembler para convertir DatosListaUsuario en EntityModel.
    @Autowired
    private DatosListaTopicosModelAssembler datosListaTopicosModelAssembler;


    @Transactional
    @PostMapping
    public ResponseEntity crear(@RequestBody @Valid DatosRegistroTopico datos, UriComponentsBuilder uriBuilder) {
        System.out.println(datos);

        var detalleTopico = topicoService.crear(datos);
        var uri = uriBuilder.path("/topicos/{id}").buildAndExpand(detalleTopico.id()).toUri();
        return ResponseEntity.created(uri).body(detalleTopico);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosListaTopicos>>> listar(@RequestParam(required = false) String curso,
                                                                             @RequestParam(required = false) Integer anio,
                                                                             @SortDefault(sort = "fechaCreacion", direction = Sort.Direction.ASC)
                                                                            @PageableDefault(size = 10)
                                                                            Pageable paginacion) {
        Page<DatosListaTopicos> pagina = topicoService.listar(paginacion, curso, anio);

        var lista = pagedResourcesAssembler.toModel(pagina, datosListaTopicosModelAssembler);
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {
        var topico = topicoService.detallar(id);

        return ResponseEntity.ok(new DatosDetalleTopico(topico));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionTopico datos) {
        System.out.println(datos);
        Topico detalleTopico = topicoService.actualizar(id, datos);

        // 2. Verificar si existe un Topico, devolver 200
        if (detalleTopico != null) {
            return ResponseEntity.ok(new DatosDetalleTopico(detalleTopico));
        }

        //Si NO existe, devolver 404
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        topicoService.elimiar(id);

        return ResponseEntity.noContent().build();
    }
}
