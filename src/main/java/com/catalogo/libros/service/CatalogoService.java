package com.catalogo.libros.service;

import com.catalogo.libros.entity.Autor;
import com.catalogo.libros.entity.Libro;
import com.catalogo.libros.repository.AutorRepository;
import com.catalogo.libros.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CatalogoService {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;

    public CatalogoService(AutorRepository autorRepository,
                           LibroRepository libroRepository) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
    }

    // ================= AUTORES =================

    @Transactional(readOnly = true)
    public void listarAutores() {

        var autores = autorRepository.findAll();

        if (autores.isEmpty()) {
            System.out.println("❌ No hay autores registrados");
            return;
        }

        autores.forEach(autor -> {

            String fallecimiento = (autor.getAnioMuerte() == null)
                    ? "Vivo"
                    : autor.getAnioMuerte().toString();

            String libros = autor.getLibros().isEmpty()
                    ? "Sin libros registrados"
                    : autor.getLibros()
                    .stream()
                    .map(Libro::getTitulo)
                    .distinct()
                    .reduce((a, b) -> a + ", " + b)
                    .orElse("");

            System.out.println("""
            ✍️ Autor: %s
            📅 Nacimiento: %d
            🕊️ Fallecimiento: %s
            📚 Libros: %s
            --------------------------------------------------
            """.formatted(
                    autor.getApellidoNombre(),
                    autor.getAnioNacimiento(),
                    fallecimiento,
                    libros
            ));
        });
    }
    @Transactional(readOnly = true)
    public void listarLibrosPorIdioma(String idiomaIngresado) {

        String idioma = idiomaIngresado.trim().toUpperCase();

        var libros = libroRepository.findByIdiomaIgnoreCase(idioma);

        if (libros.isEmpty()) {
            System.out.println("❌ No hay libros en el idioma: " + idioma);
            return;
        }

        libros.forEach(l -> {
            System.out.println("""
        📘 Título: %s
        ✍️ Autor: %s
        🌍 Idioma: %s
        ⬇️ Descargas: %,d
        --------------------------------
        """.formatted(
                    l.getTitulo(),
                    l.getAutor().getApellidoNombre(),
                    l.getIdioma().toUpperCase(),
                    l.getNumeroDescargas()
            ));
        });
    }

    @Transactional(readOnly = true)
    public void buscarLibroPorTitulo(String tituloIngresado) {

        String titulo = tituloIngresado.trim();

        // Validación básica
        if (titulo.isEmpty() || titulo.length() < 2) {
            System.out.println("""
        ❌ Libro no encontrado.
        👉 Por favor intente con otro nombre.
        """);
            return;
        }

        var resultados = libroRepository.findByTituloContainingIgnoreCase(titulo);

        if (resultados.isEmpty()) {
            System.out.println("""
        ❌ Libro no encontrado.
        👉 Por favor intente con otro nombre.
        """);
            return;
        }

        resultados.forEach(l -> {
            System.out.println("""
        📘 Título: %s
        ✍️ Autor: %s
        🌍 Idioma: %s
        ⬇️ Descargas: %,d
        --------------------------------
        """.formatted(
                    l.getTitulo(),
                    l.getAutor().getApellidoNombre(),
                    l.getIdioma().toUpperCase(),
                    l.getNumeroDescargas()
            ));
        });
    }


}
