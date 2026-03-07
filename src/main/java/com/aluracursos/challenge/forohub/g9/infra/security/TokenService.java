package com.aluracursos.challenge.forohub.g9.infra.security;

import com.aluracursos.challenge.forohub.g9.domain.usuario.Usuario;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@Service
public class TokenService {

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private int expiraEnHH;

    public String generarToken(Usuario usuario) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return  JWT.create()
                    .withIssuer("API ForoHub.G9")    // la aplicacion en que se autentica
                    .withSubject(usuario.getUser())  // datos de usuario que se autentica
                    .withExpiresAt(fechaExperacion()) // duracion del token
                    .sign(algorithm);
        } catch (JWTCreationException exception){
            throw new RuntimeException("Error al generar el token JWT", exception);
        }
    }

    private Instant fechaExperacion() {
        return LocalDateTime.now().plusHours(expiraEnHH).toInstant(ZoneOffset.of("-03:00"));
    }

    public String getSubject(String tokenJWT) {
        try {
            Algorithm algorithm = Algorithm.HMAC256(secret);
            return JWT.require(algorithm)
                    .withIssuer("API ForoHub.G9")    // la aplicacion en que se autentica
                    .build()
                    .verify(tokenJWT)
                    .getSubject();
        } catch (JWTVerificationException exception){
            throw  new RuntimeException("Token invalido o expirado");
        }
    }
}
