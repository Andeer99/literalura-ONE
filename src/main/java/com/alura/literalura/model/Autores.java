package com.alura.literalura.model;

import java.util.ArrayList;
import java.util.List;

public class Autores {
    private String nombre;
    private int fechaNacimiento;
    private int fechaFallecimiento;
    private List<Libros> libro = new ArrayList<>();

    /*
    public Autores(){}

    public Autores(String nombre, int fechaNacimiento, int fechaFallecimiento){
        this.nombre = nombre;
        this.fechaNacimiento = fechaNacimiento;
        this.fechaFallecimiento = fechaFallecimiento;
    }

     */

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
