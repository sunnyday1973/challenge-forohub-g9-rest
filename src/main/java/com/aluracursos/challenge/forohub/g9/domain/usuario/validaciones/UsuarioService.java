package com.aluracursos.challenge.forohub.g9.domain.usuario.validaciones;

import com.aluracursos.challenge.forohub.g9.domain.exepciones.ValidacionException;
import com.aluracursos.challenge.forohub.g9.domain.usuario.Usuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.UsuarioRepository;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosActualizacionUsuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosRegistroUsuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosResultadoRegistroUsuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;


@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private List<ValidadorDeNuevoUsuario> validadores;

    public DatosResultadoRegistroUsuario crear(DatosRegistroUsuario datos) {
        // La API no debe permitir el registro de login duplicados
        if (usuarioRepository.existsByUserIgnoreCase(datos.user())) {
            throw new ValidacionException("Usuario duplicado: ese login no esta disponible");
        }

        // validaciones
        //validadores.forEach(v -> v.validar(datos));

        var usuario = usuarioRepository.save(new Usuario(datos));
        return new DatosResultadoRegistroUsuario(usuario);
    }

    public Page<Usuario> listar(Pageable paginacion, String nombres, String apellidos, String correo) {
        return usuarioRepository.buscarDinamico(nombres, apellidos, correo, paginacion);
    }

    public Usuario actualizar(Long id, DatosActualizacionUsuario datos) {
        // 1. Buscar el usuario por ID
        Optional<Usuario> usuarioExistente = usuarioRepository.findById(id);

        // 2. Verificar si existe con isPresent()
        if (!usuarioExistente.isPresent()) {
            throw new ValidacionException("No existe un usuario con el id informado");
        }
        if (datos.password() != null && datos.password().trim().isEmpty()) {
            throw new ValidacionException("El password no puede estar vacío si se incluye en la petición");
        }
        // 3. Si existe, actualizarlo
        var usuario = usuarioExistente.get();
        usuario.actualizarDatosUsuario(id, datos);
        System.out.println(usuario.toString());
        return usuario;
    }

    public void eliminar(Long id) {
        Optional<Usuario>  usuarioExistente = usuarioRepository.findById(id);

        if (!usuarioExistente.isPresent()) {
            throw new ValidacionException("No existe un usuario con el id informado");
        }

        var usuario = usuarioRepository.getReferenceById(id);
        usuario.desactivar();
    }
}
