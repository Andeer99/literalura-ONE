package com.alura.literalura.model;

import java.util.List;

public class Libros {
    private int idLibro;
    private Autores autores;

    public Libros(String titulo) {
    }

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
}
