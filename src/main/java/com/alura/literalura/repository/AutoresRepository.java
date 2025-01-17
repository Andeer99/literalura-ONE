package com.alura.literalura.repository;

import com.alura.literalura.model.Autores;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AutoresRepository extends JpaRepository<Autores, Integer> {
    @Query("SELECT a FROM Autores a WHERE a.nombre = :nombre")
    Optional<Autores> findAutorByNombre(@Param("nombre") String nombre);

    @Query("SELECT a FROM Autores a LEFT JOIN FETCH a.libro l WHERE a.fechaNacimiento <= :ano AND (a.fechaFallecimiento IS NULL OR a.fechaFallecimiento >= :ano)")
    List<Autores> findAutoresVivosEnAnoConLibros(@Param("ano") int ano);




}
