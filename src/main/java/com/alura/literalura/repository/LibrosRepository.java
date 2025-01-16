package com.alura.literalura.repository;

import com.alura.literalura.model.Autores;
import com.alura.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libros, Autores> {

}
