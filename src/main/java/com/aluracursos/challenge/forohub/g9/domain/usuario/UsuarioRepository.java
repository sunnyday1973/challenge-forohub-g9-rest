package com.aluracursos.challenge.forohub.g9.domain.usuario;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;


public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    UserDetails findByUser(String login);

    boolean existsByUserIgnoreCase(String login);

    @Query("""
        SELECT u
        FROM Usuario u 
        WHERE u.status = true
          AND (:nombres IS NULL OR u.nombres LIKE %:nombres%)
          AND (:apellidos IS NULL OR u.apellidos LIKE %:apellidos%)
          AND (:correo IS NULL OR u.email = :correo)
        """)
    Page<Usuario> buscarDinamico(
            @Param("nombres") String nombres,
            @Param("apellidos") String apellidos,
            @Param("correo") String correo,
            Pageable pageable);

    @Query("""
        SELECT u.status
        FROM Usuario u
        WHERE u.id = :id
        """)
    boolean findActivoById(Long id);
}
