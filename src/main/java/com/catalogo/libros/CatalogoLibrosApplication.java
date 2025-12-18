package com.catalogo.libros;

import com.catalogo.libros.entity.*;
import com.catalogo.libros.repository.*;
import org.springframework.boot.*;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.Transactional;
import com.catalogo.libros.service.CatalogoService;


import java.util.Scanner;

@SpringBootApplication
public class CatalogoLibrosApplication implements CommandLineRunner {

    private final AutorRepository autorRepository;
    private final LibroRepository libroRepository;
    private String formatearIdioma(String idioma) {
        return idioma.toUpperCase();
    }
    private final CatalogoService catalogoService;

    public CatalogoLibrosApplication(
            AutorRepository autorRepository,
            LibroRepository libroRepository,
            CatalogoService catalogoService
    ) {
        this.autorRepository = autorRepository;
        this.libroRepository = libroRepository;
        this.catalogoService = catalogoService;
    }


    public static void main(String[] args) {
        SpringApplication.run(CatalogoLibrosApplication.class, args);
    }


    @Override
    public void run(String... args) {
        Scanner sc = new Scanner(System.in);
        int opcion;

        do {
            mostrarMenu();
            opcion = sc.nextInt();
            sc.nextLine(); // limpiar buffer

            switch (opcion) {
                case 1 -> {
                    System.out.print("Ingrese título: ");
                    String titulo = sc.nextLine();

                    // 1️⃣ Buscar en BD o API y mostrar
                    catalogoService.buscarLibroPorTitulo(titulo);

                    // 2️⃣ Guardar automáticamente desde la API
                    catalogoService.buscarYGuardarLibrosDesdeApi(titulo);
                }


                case 2 -> catalogoService.listarLibros();

                case 3 -> catalogoService.listarAutores();

                case 4 -> {
                    System.out.print("Ingrese año: ");
                    int anio = sc.nextInt();
                    sc.nextLine();
                    catalogoService.autoresVivosPorAnio(anio);
                }
                case 5 -> {
                    System.out.print("Ingrese idioma (es, en, fr, pt): ");
                    String idioma = sc.nextLine();
                    catalogoService.listarLibrosPorIdioma(idioma);
                }
                case 0 -> salidaElegante();
                default -> System.out.println("❌ Opción inválida");
            }


        } while (opcion != 0);
    }

    // ================= MENÚ =================

    private void mostrarMenu() {
        System.out.println("""
        ╔══════════════════════════════╗
        ║      📚 CATÁLOGO LIBROS      ║
        ╠══════════════════════════════╣
        ║ 1️⃣ Buscar libro por título  ║
        ║ 2️⃣ Listar libros            ║
        ║ 3️⃣ Listar autores           ║
        ║ 4️⃣ Autores vivos por año    ║
        ║ 5️⃣ Libros por idioma        ║
        ║ 0️⃣ Salir                    ║
        ╚══════════════════════════════╝
        """);
    }

    // ============== FUNCIONES ==============

    private void salidaElegante() {
        System.out.println("""
        🌙 Gracias por usar el Catálogo de Libros
        ✨ Aplicación cerrada correctamente
        👋 ¡Hasta pronto!
        """);
    }

    // ========== DATOS DE PRUEBA ==========
    private void cargarDatosIniciales() {

        if (autorRepository.count() > 0) {
            return; // evita duplicar datos
        }

        Autor garcia = autorRepository.save(
                new Autor("Gabriel García Márquez", 1927, 2014)
        );
        Autor rowling = autorRepository.save(
                new Autor("J. K. Rowling", 1965, null)
        );
        Autor tolkien = autorRepository.save(
                new Autor("J. R. R. Tolkien", 1892, 1973)
        );
        Autor orwell = autorRepository.save(
                new Autor("George Orwell", 1903, 1950)
        );
        Autor asimov = autorRepository.save(
                new Autor("Isaac Asimov", 1920, 1992)
        );
        Autor murakami = autorRepository.save(
                new Autor("Haruki Murakami", 1949, null)
        );
        Autor king = autorRepository.save(
                new Autor("Stephen King", 1947, null)
        );
        Autor eco = autorRepository.save(
                new Autor("Umberto Eco", 1932, 2016)
        );

        // 📚 Literatura
        libroRepository.save(new Libro("Cien años de soledad", "ES", garcia,1250000));
        libroRepository.save(new Libro("El amor en los tiempos del cólera", "ES", garcia,1258));
        libroRepository.save(new Libro("Crónica de una muerte anunciada", "ES", garcia,2369));

        // 🧙 Fantasía
        libroRepository.save(new Libro("Harry Potter y la cámara secreta", "EN", rowling,58214));
        libroRepository.save(new Libro("Harry Potter y el prisionero de Azkaban", "EN", rowling,1258793));

        libroRepository.save(new Libro("El Señor de los Anillos", "EN", tolkien,25321));
        libroRepository.save(new Libro("El Hobbit", "EN", tolkien,179856));
        libroRepository.save(new Libro("El Silmarillion", "EN", tolkien,985231));

        // 📖 Distopía
        libroRepository.save(new Libro("1984", "EN", orwell,23589));
        libroRepository.save(new Libro("Rebelión en la granja", "EN", orwell,78954));
        libroRepository.save(new Libro("Fahrenheit 451", "EN", orwell,498756));

        // 🚀 Ciencia ficción
        libroRepository.save(new Libro("Fundación", "EN", asimov,147852));
        libroRepository.save(new Libro("Yo, Robot", "EN", asimov,2365159));
        libroRepository.save(new Libro("El fin de la eternidad", "EN", asimov,4821300));
        libroRepository.save(new Libro("Dune", "EN", asimov,782549));

        // 🇯🇵 Literatura japonesa
        libroRepository.save(new Libro("Kafka en la orilla", "JP", murakami,35698012));
        libroRepository.save(new Libro("Tokio Blues", "JP", murakami,742568032));
        libroRepository.save(new Libro("1Q84", "JP", murakami,69821354));

        // 😱 Terror
        libroRepository.save(new Libro("It", "EN", king,12035698));
        libroRepository.save(new Libro("El resplandor", "EN", king,45210));
        libroRepository.save(new Libro("Cementerio de animales", "EN", king,12584));

        // 🧠 Ensayo / filosofía
        libroRepository.save(new Libro("El nombre de la rosa", "IT", eco,78542));
        libroRepository.save(new Libro("El péndulo de Foucault", "IT", eco,2365481));
        libroRepository.save(new Libro("Sapiens", "EN", eco,302568));
        libroRepository.save(new Libro("Homo Deus", "EN", eco,19835));
        libroRepository.save(new Libro("El código Da Vinci", "EN", eco,236548));


        libroRepository.save(new Libro("Rayuela", "ES", garcia,58746));
        libroRepository.save(new Libro("La sombra del viento", "ES", garcia,25846));
        libroRepository.save(new Libro("El alquimista", "PT", eco,905782));
        libroRepository.save(new Libro("Crimen y castigo", "RU", eco,326510));
        libroRepository.save(new Libro("Los miserables", "FR", eco,705896));
        libroRepository.save(new Libro("La metamorfosis", "DE", eco,1254788));
        libroRepository.save(new Libro("Drácula", "EN", king,45216));
        libroRepository.save(new Libro("Frankenstein", "EN", king,60534));
        libroRepository.save(new Libro("Solaris", "PL", asimov,546238));
        libroRepository.save(new Libro("Neuromante", "EN", asimov,1254));
        libroRepository.save(new Libro("El perfume", "DE", eco,72365984));
        libroRepository.save(new Libro("Don Quijote de la Mancha", "ES", garcia,459872631));
        libroRepository.save(new Libro("Ulises", "EN", eco,12037));
        libroRepository.save(new Libro("Madame Bovary", "FR", eco,15487));

    }
}