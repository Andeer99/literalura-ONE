package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "libros")
public class Libros {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nombre;
    @Column(name = "lenguaje", nullable = true)
    private String lenguaje;
    @Column(name = "descargas", nullable = true)
    private Double descargas;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "autor_id", nullable = true) // Clave foránea que conecta con la tabla "autores"
    private Autores autores;


    //public Libros(String titulo) {}

    public int getIdLibro() {
        return id;
    }

    public void setIdLibro(int idLibro) {
        this.id = idLibro;
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

    public String getLenguaje() {
        return lenguaje;
    }

    public void setLenguaje(String lenguaje) {
        this.lenguaje = lenguaje;
    }

    public Double getDescargas() {
        return descargas;
    }

    public void setDescargas(Double descargas) {
        this.descargas = descargas;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Libros libros = (Libros) o;
        return Objects.equals(id, libros.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
