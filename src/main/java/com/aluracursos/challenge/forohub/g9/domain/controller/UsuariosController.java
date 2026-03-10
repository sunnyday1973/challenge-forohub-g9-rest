package com.aluracursos.challenge.forohub.g9.domain.controller;

import com.aluracursos.challenge.forohub.g9.domain.usuario.DatosListaUsuariosModelAssembler;
import com.aluracursos.challenge.forohub.g9.domain.usuario.Usuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.UsuarioRepository;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosActualizacionUsuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosDetalleUsuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosListaUsuarios;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosRegistroUsuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.validaciones.UsuarioService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.data.web.SortDefault;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;


@SecurityRequirement(name = "bearer-key")
@RestController
@RequestMapping("/usuarios")
public class UsuariosController {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private UsuarioService usuarioService;

    // PagedResourcesAssembler se usa para convertir una Page en un PagedModel.
    @Autowired
    private PagedResourcesAssembler<DatosListaUsuarios> pagedResourcesAssembler;

    // Inyectamos nuestro DatosListaUsuariosModelAssembler para convertir DatosListaUsuario en EntityModel.
    @Autowired
    private DatosListaUsuariosModelAssembler datosListaUsuariosModelAssembler;


    @Transactional
    @PostMapping
    public ResponseEntity crear(@RequestBody @Valid DatosRegistroUsuario datos, UriComponentsBuilder uriBuilder) {
        System.out.println(datos);

        var detalleUsuario = usuarioService.crear(datos);
        var uri = uriBuilder.path("/usuarios/{id}").buildAndExpand(detalleUsuario.id()).toUri();
        return ResponseEntity.created(uri).body(detalleUsuario);
    }

    @GetMapping
    public ResponseEntity<PagedModel<EntityModel<DatosListaUsuarios>>> listar(@RequestParam(required = false) String nombres,
                                                                              @RequestParam(required = false) String apellidos,
                                                                              @RequestParam(required = false) String correo,
                                                                              @SortDefault(sort = "fechaCreacion", direction = Sort.Direction.ASC)
                                                                            @PageableDefault(size = 10)
                                                                            Pageable paginacion) {
        Page<DatosListaUsuarios> pagina = usuarioService.listar(paginacion, nombres, apellidos, correo).map(DatosListaUsuarios::new);

        var lista = pagedResourcesAssembler.toModel(pagina, datosListaUsuariosModelAssembler);
        System.out.println(lista);
        return ResponseEntity.ok(lista);
    }

    @GetMapping("/{id}")
    public ResponseEntity detallar(@PathVariable Long id) {

        var usuario = usuarioRepository.getReferenceById(id);

        return ResponseEntity.ok(new DatosDetalleUsuario(usuario));
    }

    @Transactional
    @PutMapping("/{id}")
    public ResponseEntity actualizar(@PathVariable Long id, @RequestBody @Valid DatosActualizacionUsuario datos) {
        System.out.println(datos);
        Usuario detalleUsuario = usuarioService.actualizar(id, datos);

        // 2. Verificar si existe un Usuario, devolver 200
        if (detalleUsuario != null) {
            return ResponseEntity.ok(new DatosDetalleUsuario(detalleUsuario));
        }

        //Si NO existe, devolver 404
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity eliminar(@PathVariable Long id) {
        usuarioService.eliminar(id);

        return ResponseEntity.noContent().build();
    }
}
