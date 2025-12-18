package com.catalogo.libros.repository;

import com.catalogo.libros.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LibroRepository extends JpaRepository<Libro, Long> {

    List<Libro> findByTituloContainingIgnoreCase(String titulo);
    List<Libro> findByIdiomaIgnoreCase(String idioma);
}
