package com.aluracursos.challenge.forohub.g9.domain.controller;

import com.aluracursos.challenge.forohub.g9.domain.usuario.UsernamePasswordAuthenticationToken;
import com.aluracursos.challenge.forohub.g9.domain.usuario.Usuario;
import com.aluracursos.challenge.forohub.g9.infra.security.DatosTokenJWT;
import com.aluracursos.challenge.forohub.g9.infra.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/login")
public class AutenticacionController {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping
    public ResponseEntity inicarSesion(@RequestBody @Valid UsernamePasswordAuthenticationToken datos) {
        var authenticationToken = new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(datos.user(), datos.password());
        var autenticacion = manager.authenticate(authenticationToken);
        var tokenJWT = tokenService.generarToken((Usuario) autenticacion.getPrincipal());

        return ResponseEntity.ok(new DatosTokenJWT(tokenJWT));
    }
}
