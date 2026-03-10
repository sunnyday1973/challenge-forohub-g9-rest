package com.aluracursos.challenge.forohub.g9.domain.usuario;

import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosListaUsuarios;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

// La anotación @Component indica que esta clase es un bean de Spring
// y se registrará automáticamente en el contexto de la aplicación.
@Component
public class DatosListaUsuariosModelAssembler implements
        RepresentationModelAssembler<DatosListaUsuarios, EntityModel<DatosListaUsuarios>> {

    // El metodo toModel convierte una instancia de DatosListaUsuarios en un EntityModel,
    // que es una representación envolvente que proporciona una estructura estable
    // para el JSON y puede incluir links adicionales.
    @Override
    @NonNull
    public EntityModel<DatosListaUsuarios> toModel(@NonNull DatosListaUsuarios usuario) {
        return EntityModel.of(usuario);
    }
}