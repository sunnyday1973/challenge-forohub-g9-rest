package com.aluracursos.challenge.forohub.g9.domain.usuario;

import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosActualizacionUsuario;
import com.aluracursos.challenge.forohub.g9.domain.usuario.dto.DatosRegistroUsuario;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Table(name="usuarios")
@Entity(name="Usuario")
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Usuario implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "login")
    String user;
    String password;
    String apellidos;
    String nombres;
    String email;
    LocalDateTime fechaCreacion;
    LocalDateTime fechaActualizacion;
    @Column(name = "activo")
    Boolean status;

    public Usuario(DatosRegistroUsuario datos) {
        this.id = null;
        this.status = true;
        this.fechaCreacion = LocalDateTime.now();
        this.fechaActualizacion = null;
        if (datos.nombres() != null) {
            this.nombres = datos.nombres();
        }
        if (datos.apellidos() != null) {
            this.apellidos = datos.apellidos();
        }
        if (datos.email() != null) {
            this.email = datos.email();
        }
        if (datos.user() != null) {
            this.user = datos.user();
        }
        if (datos.password() != null) {
            this.password = datos.password();
        }
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_USER"));
    }

    @Override
    public String getUsername() {
        return this.user;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    public void desactivar() {
        this.status = false;
    }

    public void actualizarDatosUsuario(Long id, DatosActualizacionUsuario datos) {
        this.id = id;
        if (datos.nombres() != null) {
            this.nombres = datos.nombres();
        }
        if (datos.apellidos() != null) {
            this.apellidos = datos.apellidos();
        }
        if (datos.email() != null) {
            this.email = datos.email();
        }
        if (datos.password() != null) {
            this.password = datos.password();
        }
        this.fechaActualizacion = (datos.fechaActualizacion() != null) ? datos.fechaActualizacion() : LocalDateTime.now();
    }
}
