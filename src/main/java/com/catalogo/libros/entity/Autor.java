package com.catalogo.libros.entity;

import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "autores")
public class Autor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre;
    private Integer anioNacimiento;
    private Integer anioMuerte; // null = sigue vivo
    @OneToMany(mappedBy = "autor")
    private List<Libro> libros;


    public Autor() {}

    public Autor(String nombre, Integer anioNacimiento, Integer anioMuerte) {
        this.nombre = nombre;
        this.anioNacimiento = anioNacimiento;
        this.anioMuerte = anioMuerte;
    }
    public String getApellidoNombre() {
        String[] partes = nombre.split(" ", 2);
        if (partes.length == 2) {
            return partes[1] + ", " + partes[0];
        }
        return nombre;
    }

    public Long getId() {
        return id;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getAnioNacimiento() {
        return anioNacimiento;
    }

    public Integer getAnioMuerte() {
        return anioMuerte;
    }
    public List<Libro> getLibros() {
        return libros;
    }



}
