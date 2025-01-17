package com.alura.literalura.repository;

import com.alura.literalura.model.Autores;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AutorService {

    @Autowired
    private AutoresRepository autoresRepository;

    public List<Autores> obtenerAutoresVivosConLibros(int ano) {
        return autoresRepository.findAutoresVivosEnAnoConLibros(ano);
    }
}
