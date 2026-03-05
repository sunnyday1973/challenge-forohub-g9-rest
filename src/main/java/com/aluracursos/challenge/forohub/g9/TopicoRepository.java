package com.aluracursos.challenge.forohub.g9;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface TopicoRepository  extends JpaRepository<Topico, Long> {
    boolean existsByTituloIgnoreCase(String titulo);

    Page<Topico> findAllByStatusTrue(Pageable paginacion);

    // Por curso
    Page<Topico> findAllByStatusTrueAndCursoContainingIgnoreCase(String curso, Pageable paginacion);

    // Por año (extrae año de fechaCreacion)
    Page<Topico> findAllByStatusTrueAndFechaCreacionYear(Integer anio, Pageable paginacion);

    // Por curso + año
    Page<Topico> findAllByStatusTrueAndCursoContainingIgnoreCaseAndFechaCreacionYear(
            String curso, Integer anio, Pageable paginacion);

    boolean existsByAutor(Long autor);

}