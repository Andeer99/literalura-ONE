package com.alura.literalura.model;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;


@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosLibros(
        @JsonProperty("title") String libro,
        @JsonProperty("authors") List<DatosAutores> autores,
        @JsonProperty("languages") List<String> lenguaje,
        @JsonProperty("download_count") Double descargas
) {}
