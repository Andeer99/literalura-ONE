package com.alura.literalura.repository;

import com.alura.literalura.model.Libros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LibroService {
    @Autowired
    private LibrosRepository librosRepository;

    public List<Libros> obtenerLibrosPorIdioma(String idioma) {
        return librosRepository.findLibrosByIdioma(idioma);
    }
}
