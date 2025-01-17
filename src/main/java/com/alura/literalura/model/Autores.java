package com.alura.literalura.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "autores")
public class Autores {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; // Agrega un ID para identificar al autor en la base de datos

    private String nombre;
    private int fechaNacimiento;
    private int fechaFallecimiento;
    @OneToMany(mappedBy = "autores", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Libros> libro = new ArrayList<>();

    public int getFechaFallecimiento() {
        return fechaFallecimiento;
    }

    public void setFechaFallecimiento(int fechaFallecimiento) {
        this.fechaFallecimiento = fechaFallecimiento;
    }

    public int getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(int fechaNacimiento) {
        this.fechaNacimiento = fechaNacimiento;
    }


    public String getNombre() {
        return nombre;
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
}
