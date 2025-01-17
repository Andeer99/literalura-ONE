package com.alura.literalura.principal;

import com.alura.literalura.model.Autores;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.model.Libros;
import com.alura.literalura.repository.LibrosRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibrosResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.*;
import java.util.stream.Collectors;
/**
 * @author Álvarez Galicia Raúl Alexander
 * Clase principal que muestra el menú principal, se hace uso del framework Spring,
 * consultas a la API de Gutendex, uso de PostgreSQL para la permanencia de datos
 * al consultar libros,
 */
@Component
public class Principal {
    private Scanner sc = new Scanner(System.in);
    @Autowired
    private ConsumoAPI consumoApi = new ConsumoAPI();
    @Autowired
    private ConvierteDatos conversor = new ConvierteDatos();
    @Autowired
    private LibrosRepository librosRepository;
    private List<Libros> libro;
    private final String URL_BASE = "https://gutendex.com/";
    public void muestraElMenu() {
        var opcion = -1;
        while (opcion != 0) {
            var menu = """
                    
                    ******Librería Literalura***** 
                    1 - Buscar libro por título 
                    2 - Listar libros registrados
                    3 - Listar autores registrados
                    4 - Listar autores vivos en un determinado año
                    5 - Listar libros por idioma
                    
                    0 - Salir
                    """;
            System.out.println(menu);
            opcion = sc.nextInt();
            sc.nextLine();
            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;
                case 2:
                    listarLibrosConsultados();
                    break;
                case 3:

                    break;

                case 4:

                    break;

                case 5:

                    break;
                case 0:
                    System.out.println("Cerrando la aplicación...");
                    break;
                default:
                    System.out.println("Opción inválida");
            }
        }
    }
    private void buscarLibroPorTitulo() {
        while (true) {
            System.out.println("Ingrese el título del libro: ");
            var titulo = sc.nextLine().trim();
            if (titulo.isEmpty()) {
                System.out.println("El título no puede estar vacío. Por favor, ingresa un título válido.");
                continue;
            }
            try {
                // Buscar en la base de datos y tomar el primero
                Optional<Libros> libroBD = librosRepository.findByNombreContainingIgnoreCase(titulo).stream().findFirst();
                if (libroBD.isPresent()) {
                    System.out.println("----- LIBRO -----");
                    System.out.println("Título: " + libroBD.get().getNombre());
                    if (libroBD.get().getAutores() != null) {
                        System.out.println("Autor: " + libroBD.get().getAutores().getNombre());
                    } else {
                        System.out.println("Autor: Desconocido");
                    }
                    System.out.println("Idioma: en");
                    System.out.println("Número de descargas: N/A");
                    System.out.println("-------------------------------");
                } else {
                    // Buscar en la API y tomar el primero
                    String urlBusqueda = URL_BASE + "books/?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                    String jsonRespuesta = consumoApi.obtenerDatos(urlBusqueda);
                    LibrosResponse response = conversor.obtenerDatos(jsonRespuesta, LibrosResponse.class);
                    response.getResults().stream().findFirst().ifPresent(libro -> {
                        System.out.println("----- LIBRO -----");
                        System.out.println("Título: " + libro.libro());
                        if (libro.autores() != null && !libro.autores().isEmpty()) {
                            System.out.println("Autor: " + libro.autores().get(0).nombre()); // Mostrar solo el primer autor
                        } else {
                            System.out.println("Autor: Desconocido");
                        }
                        System.out.println("Idioma: en");
                        System.out.println("Número de descargas: " + (libro.descargas() != null ? libro.descargas() : "N/A"));
                        System.out.println("-------------------------------");
                        // Guardar en la base de datos
                        Libros nuevoLibro = new Libros();
                        nuevoLibro.setNombre(libro.libro());
                        if (libro.autores() != null && !libro.autores().isEmpty()) {
                            Autores autor = new Autores();
                            autor.setNombre(libro.autores().get(0).nombre());
                            nuevoLibro.setAutores(autor);
                        } else {
                            nuevoLibro.setAutores(null);
                        }
                        librosRepository.save(nuevoLibro);
                    });
                    if (response.getResults().isEmpty()) {
                        System.out.println("No se encontraron libros con el título: " + titulo);
                    }
                }
            } catch (Exception e) {
                System.out.println("Ocurrió un error al buscar el libro: " + e.getMessage());
            }
            // Preguntar si desea continuar
            String respuesta;
            do {
                System.out.println("¿Deseas buscar más libros? (si | no)");
                respuesta = sc.nextLine().trim().toLowerCase();
                if (respuesta.equals("no")) {
                    System.out.println("Regresando al menú principal...");
                    return;
                } else if (!respuesta.equals("si")) {
                    System.out.println("Opción inválida. Por favor, ingresa 'si' o 'no'.");
                }
            } while (!respuesta.equals("si"));
        }
    }

    private void listarLibrosConsultados(){
        libro = librosRepository.findAll();
        libro.stream()
                .sorted(Comparator.comparing(Libros::getNombre))
                .forEach(System.out::println);
    }

}


