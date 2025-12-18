package com.catalogo.libros.repository;

import com.catalogo.libros.entity.Autor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface AutorRepository extends JpaRepository<Autor, Long> {

    @Query("""
        SELECT a FROM Autor a
        WHERE a.anioNacimiento <= :anio
        AND (a.anioMuerte IS NULL OR a.anioMuerte > :anio)
    """)
    List<Autor> autoresVivosEnAnio(@Param("anio") Integer anio);
    Optional<Autor> findByNombre(String nombre);
}
