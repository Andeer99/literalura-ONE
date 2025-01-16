package com.alura.literalura.repository;

import com.alura.literalura.model.Libros;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LibrosRepository extends JpaRepository<Libros, Integer> {

    // Método para buscar libros por nombre (título)
    List<Libros> findByNombreContainingIgnoreCase(String nombre);

    // Consulta personalizada para buscar libros por nombre exacto usando JPQL
    @Query("SELECT l FROM Libros l WHERE LOWER(l.nombre) = LOWER(:nombre)")
    List<Libros> buscarPorNombreExacto(String nombre);

    // Consulta para obtener todos los libros de un autor específico
    @Query("SELECT l FROM Libros l WHERE l.autores.nombre = :nombreAutor")
    List<Libros> findByAutorNombre(String nombreAutor);
}
