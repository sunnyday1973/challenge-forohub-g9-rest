package com.aluracursos.challenge.forohub.g9.infra.security;


import com.aluracursos.challenge.forohub.g9.domain.usuario.UsuarioRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    UsuarioRepository repository;

    @Autowired
    private TokenService tokenService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        System.out.println("se llamo al Filter");
        var tokenJWT = recuperarToken(request);
        System.out.println("token enviado: "+tokenJWT);
        if(tokenJWT != null) {
            var subject = tokenService.getSubject(tokenJWT);
            System.out.println("subject: "+subject);
            var usuario = repository.findByUser(subject);

            // autenticar al usuario
            var authenticationToken = new UsernamePasswordAuthenticationToken(usuario, null, usuario.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            System.out.println("usuario logeado");
        }
        filterChain.doFilter(request, response);
    }

    private String recuperarToken(HttpServletRequest request) {
        var authorizationHeader = request.getHeader("Authorization");
        if(authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        }
        //throw new RuntimeException("Token JWT no enviado en el encabezado de authorizacion");
        return null;
    }
}
