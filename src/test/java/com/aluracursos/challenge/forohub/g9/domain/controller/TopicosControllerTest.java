package com.aluracursos.challenge.forohub.g9.domain.controller;

import com.aluracursos.challenge.forohub.g9.domain.topico.Topico;
import com.aluracursos.challenge.forohub.g9.domain.topico.TopicoRepository;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.*;
import com.aluracursos.challenge.forohub.g9.domain.topico.dto.DatosListaTopico;
import com.aluracursos.challenge.forohub.g9.domain.topico.validaciones.TopicoService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.AutoConfigureJsonTesters;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.json.JacksonTester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import java.time.LocalDateTime;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;


@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureJsonTesters
class TopicosControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private JacksonTester<DatosListaTopico> datosRegistroTopicoJson;

    @Autowired
    private JacksonTester<DatosResultadoRegistroTopico> datosResultadoRegistroTopicoJson;

    @Autowired
    private JacksonTester<DatosDetalleTopico> datosDetalleTopicoJson;

    @Autowired
    private JacksonTester<DatosListaTopico> datosListaTopicoJson;

    @MockitoBean
    private TopicoService topicoService;

    @MockitoBean
    private TopicoRepository topicoRepository;

    @Test
    @DisplayName("Devuelve error 400, cuando la request no tenga datos")
    @WithMockUser
    void crearSinBody() throws Exception {
        var response = mockMvc.perform(post("/topicos")).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.BAD_REQUEST.value());
    }

    @Test
    @DisplayName("Devuelve error 200, cuando la request  tenga datos")
    @WithMockUser
    void crearConBody() throws Exception {
        var datosResultado = new DatosResultadoRegistroTopico(1L, "Registro titulo", "Prueba"
                , LocalDateTime.now(), true, 1L, "G8");
        when(topicoService.crearTopico(any())).thenReturn(datosResultado);
        var response = mockMvc.perform(post("/topicos")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosRegistroTopicoJson.write(
                                new DatosListaTopico(
                                         1L, "Registro titulo", "Prueba", null
                                        , true, 1L, "G8"
                                )
                        ).getJson())
                )
                .andReturn().getResponse();

        var jsonEsperado = datosResultadoRegistroTopicoJson.write(datosResultado).getJson();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.CREATED.value());
        assertThat(response.getContentAsString()).isEqualTo(jsonEsperado);
    }

//    @Test
//    @DisplayName("Devuelve error 200, cuando la request no tenga datos")
//    @WithMockUser
//    void listar() throws Exception {
//        var response = mockMvc.perform(get("/topicos")).andReturn().getResponse();
//        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
//    }

    @Test
    @DisplayName("Debería retornar status 200 y la lista paginada de tópicos en formato JSON")
    @WithMockUser
    void listar() throws Exception {
        // 1. PREPARAR: Creamos la ENTIDAD Topico (que es lo que devuelve el repositorio)
        var datosRegistro = new DatosRegistroTopico(
                null,
                "Título de prueba",
                "Mensaje de prueba",
                null,
                null,
                null,
                1L,
                "Curso de Prueba",
                null
        );
        var topico = new Topico(datosRegistro);
        // Forzamos el ID si tu entidad lo permite o usa un constructor completo

        // Creamos la página de ENTIDADES
        Page<Topico> paginaSimulada = new PageImpl<>(List.of(topico));

        // 2. CONFIGURAR MOCK: Ahora los tipos coinciden (Page<Topico>)
        // Asegúrate de importar any() de org.mockito.ArgumentMatchers
        when(topicoRepository.findAllByStatusTrue(any(Pageable.class))).thenReturn(paginaSimulada);

        // 3. EJECUTAR
        var response = mockMvc.perform(get("/topicos")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn().getResponse();

        // 4. VERIFICAR
        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("_embedded");
        assertThat(response.getContentAsString()).contains("Título de prueba");
    }

    @Test
    @DisplayName("Debería retornar status 200 y el detalle del tópico solicitado por ID")
    @WithMockUser
    void detallar() throws Exception {
        var datosResultado = new DatosDetalleTopico(
                "Registro titulo", "Prueba", LocalDateTime.now(), LocalDateTime.now()
                , true, 1L, "G8"
        );

        var response = mockMvc.perform(get("/topicos/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(datosDetalleTopicoJson.write(datosResultado).getJson())
                )
                .andReturn()
                .getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
        assertThat(response.getContentAsString()).contains("Registro titulo");
    }

    @Test
    @DisplayName("Debería retornar status 204 al eliminar un tópico")
    @WithMockUser
    void eliminar() throws Exception {
        // El controlador llama a topicoService.elimiarTopico(id) y devuelve noContent
        var response = mockMvc.perform(delete("/topicos/1"))
                .andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.NO_CONTENT.value());
    }

    @Test
    @DisplayName("Debería retornar status 200 al actualizar un tópico con datos válidos")
    @WithMockUser
    void actualizar() throws Exception {
        // Datos de entrada (DTO)
        DatosActualizacionTopico datosActualizacion = new DatosActualizacionTopico("Titulo Actualizado"
                , "Mensaje Actualizado"
                , LocalDateTime.now()
                , true
                , 1L
                , "Curso Actualizado");

        // El controlador espera que el servicio devuelva un objeto Topico
        var topicoSimulado = new Topico(); // Asegúrate de que no sea null para que devuelva 200

        when(topicoService.actualizarDatosTopico(any(), any())).thenReturn(topicoSimulado);

        var response = mockMvc.perform(put("/topicos/1")
                .contentType(MediaType.APPLICATION_JSON)
                //.content("{ \"titulo\": \"itulo Actualizado\", \"mensaje\": \"Mensaje Actualizado\", \"autor\": 1 }"))
                .content(datosDetalleTopicoJson.write(
                        new DatosDetalleTopico(new Topico(1L
                                , datosActualizacion.titulo()
                                , datosActualizacion.mensaje()
                                , LocalDateTime.now()
                                , datosActualizacion.fechaActualizacion()
                                , datosActualizacion.status()
                                , datosActualizacion.autor()
                                , datosActualizacion.curso()
                        )
                        )).getJson())).andReturn().getResponse();

        assertThat(response.getStatus()).isEqualTo(HttpStatus.OK.value());
    }
}

