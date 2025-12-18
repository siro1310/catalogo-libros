package com.catalogo.libros.service;

import com.catalogo.libros.api.GutendexClient;
import com.catalogo.libros.dto.GutendexBookDTO;
import com.catalogo.libros.dto.GutendexResponseDTO;
import com.catalogo.libros.entity.Autor;
import com.catalogo.libros.entity.Libro;
import com.catalogo.libros.repository.AutorRepository;
import com.catalogo.libros.repository.LibroRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CatalogoService {

    private final LibroRepository libroRepository;
    private final AutorRepository autorRepository;
    private final GutendexClient gutendexClient;

    public CatalogoService(LibroRepository libroRepository,
                           AutorRepository autorRepository,
                           GutendexClient gutendexClient) {
        this.libroRepository = libroRepository;
        this.autorRepository = autorRepository;
        this.gutendexClient = gutendexClient;
    }

    // 🔍 BUSCAR LIBRO POR TÍTULO (API + DB)
    public void buscarLibroPorTitulo(String titulo) {

        // 1️⃣ Buscar primero en la BD
        List<Libro> librosLocales =
                libroRepository.findByTituloContainingIgnoreCase(titulo);

        if (!librosLocales.isEmpty()) {
            librosLocales.forEach(this::mostrarLibro);
            return;
        }

        // 2️⃣ Si no hay en BD → buscar en API (con paginación)
        List<GutendexBookDTO> librosApi =
                gutendexClient.buscarTodosPorTitulo(titulo);

        if (librosApi.isEmpty()) {
            System.out.println("❌ Libro no encontrado, intente con otro nombre");
            return;
        }

        librosApi.forEach(this::mostrarLibroDesdeApi);
    }

    @Transactional
    public void buscarYGuardarLibrosDesdeApi(String titulo) {

        List<GutendexBookDTO> librosApi =
                gutendexClient.buscarTodosPorTitulo(titulo);

        if (librosApi.isEmpty()) {
            System.out.println("❌ No se encontraron libros en la API");
            return;
        }

        int guardados = 0;

        for (GutendexBookDTO dto : librosApi) {

            if (libroRepository.findByTituloIgnoreCase(dto.getTitle()).isPresent()) {
                continue; // evita duplicados
            }

            Autor autor = obtenerOCrearAutor(dto);

            String tituloLibro = dto.getTitle();

// 🔒 Protección contra títulos demasiado largos
            if (tituloLibro.length() > 255) {
                tituloLibro = tituloLibro.substring(0, 255);
            }

            Libro libro = new Libro(
                    tituloLibro,
                    dto.getLanguages().isEmpty()
                            ? "ND"
                            : dto.getLanguages().get(0).toUpperCase(),
                    autor,
                    dto.getDownloadCount()
            );


            libroRepository.save(libro);
            guardados++;

        }

        System.out.println("✅ Libros guardados desde la API: " + guardados);
    }



    // 📚 LISTAR LIBROS
    public void listarLibros() {
        libroRepository.findAll()
                .forEach(this::mostrarLibro);
    }

    // ✍️ LISTAR AUTORES
    @Transactional
    public void listarAutores() {
        autorRepository.findAll()
                .forEach(a -> {
                    System.out.println("✍️ " + a.getApellidoNombre());
                    System.out.println("   Nacimiento: " + a.getAnioNacimiento());
                    System.out.println("   Fallecimiento: " +
                            (a.getAnioMuerte() != null ? a.getAnioMuerte() : "Vivo"));

                    a.getLibros().forEach(l ->
                            System.out.println("   📘 " + l.getTitulo()));
                    System.out.println();
                });
    }

    // 🟢 AUTORES VIVOS POR AÑO
    public void autoresVivosPorAnio(int anio) {
        autorRepository.autoresVivosEnAnio(anio)
                .forEach(a -> System.out.println(
                        "🟢 " + a.getApellidoNombre()
                ));
    }

    // 🌍 LIBROS POR IDIOMA
    public void listarLibrosPorIdioma(String idioma) {
        libroRepository.findByIdiomaIgnoreCase(idioma)
                .forEach(this::mostrarLibro);
    }

    // ===================== MÉTODOS AUXILIARES =====================

    private void mostrarLibro(Libro l) {
        System.out.println("""
                📘 Título: %s
                   Autor: %s
                   Idioma: %s
                   Descargas: %d
                """.formatted(
                l.getTitulo(),
                l.getAutor().getApellidoNombre(),
                l.getIdioma(),
                l.getNumeroDescargas()
        ));
    }

    private void mostrarLibroDesdeApi(GutendexBookDTO libro) {
        System.out.println("""
                📘 Título: %s
                   Autor: %s
                   Idioma: %s
                   Descargas: %d
                """.formatted(
                libro.getTitle(),
                libro.getAuthors().isEmpty()
                        ? "Desconocido"
                        : libro.getAuthors().get(0).getName(),
                libro.getLanguages().isEmpty()
                        ? "--"
                        : libro.getLanguages().get(0),
                libro.getDownloadCount()
        ));
    }
    private Autor obtenerOCrearAutor(GutendexBookDTO dto) {

        if (dto.getAuthors().isEmpty()) {
            return autorRepository.findByNombre("Autor Desconocido")
                    .orElseGet(() ->
                            autorRepository.save(
                                    new Autor("Autor Desconocido", null, null)
                            )
                    );
        }

        String nombreCompleto = dto.getAuthors().get(0).getName();

        return autorRepository.findByNombre(nombreCompleto)
                .orElseGet(() ->
                        autorRepository.save(
                                new Autor(nombreCompleto, null, null)
                        )
                );
    }

}
