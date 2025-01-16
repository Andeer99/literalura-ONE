package com.alura.literalura.service;

import com.alura.literalura.model.DatosLibros;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public class LibrosResponse {
    private List<DatosLibros> results;

    public List<DatosLibros> getResults() {
        return results;
    }

    public void setResults(List<DatosLibros> results) {
        this.results = results;
    }
}
