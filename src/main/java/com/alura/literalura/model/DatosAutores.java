package com.alura.literalura.model;
import com.fasterxml.jackson.annotation.JsonAlias;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
@JsonIgnoreProperties(ignoreUnknown = true)
public record DatosAutores(@JsonProperty("name") String nombre,
                           @JsonProperty("birth_year") Integer fechaNacimiento,
                           @JsonProperty("death_year") Integer fechaFallecimiento) {
}
