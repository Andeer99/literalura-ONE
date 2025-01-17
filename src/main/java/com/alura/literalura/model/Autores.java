package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Agrega un ID para identificar al autor en la base de datos
    private String nombre;
    @Column(name = "author_year_start", nullable = true)
    private Integer fechaNacimiento;
    @Column(name = "author_year_end", nullable = true)
    private Integer fechaFallecimiento;
    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.EAGER)
    private List<Libros> libro = new ArrayList<>();

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<Libros> getLibros() {
        return libro;
    }
    public String getFechaNacimientoTexto() {
        return fechaNacimiento != null ? fechaNacimiento.toString() : "Desconocido";
    }

    public void setFechaFallecimiento(Integer fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public void setFechaNacimiento(Integer fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }

    public String getFechaFallecimientoTexto() {
        return fechaFallecimiento != null ? fechaFallecimiento.toString() : "Desconocido";
    }

    public String getNombre() {
        return nombre != null ? nombre.toString() : "Desconocido";
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Libros> getLibro() {
        return libro;
    }

    public void setLibro(List<Libros> libro) {
        this.libro = libro;
    }
    @Override
    public String toString() {
        return "Libro = "+ libro + ", Autor= "+ nombre;

    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Autores autores = (Autores) o;
        return Objects.equals(id, autores.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
