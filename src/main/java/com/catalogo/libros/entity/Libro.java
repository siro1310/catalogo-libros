package com.catalogo.libros.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "libros")
public class Libro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titulo;
    private String idioma;
    private Integer numeroDescargas;


    @ManyToOne
    private Autor autor;

    public Libro() {}

    public Libro(String titulo, String idioma, Autor autor, Integer numeroDescargas) {
        this.titulo = titulo;
        this.idioma = idioma;
        this.autor = autor;
        this.numeroDescargas = numeroDescargas;
    }

    public Long getId() {
        return id;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getIdioma() {
        return idioma;
    }

    public Autor getAutor() {
        return autor;
    }
    public Integer getNumeroDescargas() {
        return numeroDescargas;
    }



}
