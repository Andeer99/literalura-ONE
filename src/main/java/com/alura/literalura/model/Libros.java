package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer idLibro;

    private String nombre;

    @ManyToOne(optional = true)
    @JoinColumn(name = "autor_id", nullable = true) // Clave foránea que conecta con la tabla "autores"
    private Autores autores;

    //public Libros(String titulo) {}

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public Autores getAutores() {
        return autores;
    }

    public void setAutores(Autores autores) {
        this.autores = autores;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
