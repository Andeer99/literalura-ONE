package com.alura.literalura.repository;

import com.alura.literalura.model.Autores;
import com.alura.literalura.model.Libros;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface LibrosRepository extends JpaRepository<Libros, Integer> {

    // Método para buscar libros por nombre (título)
    List<Libros> findByNombreContainingIgnoreCase(String nombre);
    @EntityGraph(attributePaths = {"autores"})
    List<Libros> findAll();
    @Query("SELECT DISTINCT l FROM Libros l LEFT JOIN FETCH l.autores")
    List<Libros> findAllWithAutoresAndLibros();
    @Query("SELECT l FROM Libros l WHERE l.autores.id = :autorId")
    List<Libros> findLibrosByAutor(@Param("autorId") Integer autorId);



}
