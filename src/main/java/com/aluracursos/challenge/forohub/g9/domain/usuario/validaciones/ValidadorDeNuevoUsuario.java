package com.aluracursos.challenge.forohub.g9.domain.usuario.validaciones;

import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosRegistroUsuario;

public interface ValidadorDeNuevoUsuario {
        void validar(DatosRegistroUsuario datos);
}