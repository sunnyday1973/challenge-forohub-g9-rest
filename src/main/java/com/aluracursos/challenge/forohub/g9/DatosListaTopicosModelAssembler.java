package com.aluracursos.challenge.forohub.g9;

import com.aluracursos.challenge.forohub.g9.dto.DatosListaTopico;
import lombok.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

// La anotación @Component indica que esta clase es un bean de Spring
// y se registrará automáticamente en el contexto de la aplicación.
@Component
public class DatosListaTopicosModelAssembler implements
        RepresentationModelAssembler<DatosListaTopico, EntityModel<DatosListaTopico>> {

    // El metodo toModel convierte una instancia de DatosListaMedico en un EntityModel,
    // que es una representación envolvente que proporciona una estructura estable
    // para el JSON y puede incluir links adicionales.
    @Override
    @NonNull
    public EntityModel<DatosListaTopico> toModel(@NonNull DatosListaTopico medico) {
        return EntityModel.of(medico);
    }
}