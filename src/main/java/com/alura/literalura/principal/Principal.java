package com.alura.literalura.principal;

import com.alura.literalura.model.Autores;
import com.alura.literalura.model.Libros;
import com.alura.literalura.repository.AutoresRepository;
import com.alura.literalura.repository.LibrosRepository;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibrosResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase principal que muestra el menú principal. Se hace uso de:
 * - Framework Spring
 * - Consultas a la API de Gutendex
 * - PostgreSQL para la persistencia de datos
 */
@Component
public class Principal {

    private final Scanner sc = new Scanner(System.in);

    @Autowired
    private ConsumoAPI consumoApi;

    @Autowired
    private ConvierteDatos conversor;

    @Autowired
    private LibrosRepository librosRepository;

    @Autowired
    private AutoresRepository autoresRepository;


    private final String URL_BASE = "https://gutendex.com/";

    /**
     * Método principal que muestra el menú y dirige a otras funcionalidades.
     */
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
            sc.nextLine(); // Consumimos la línea restante

            switch (opcion) {
                case 1:
                    buscarLibroPorTitulo();
                    break;

                case 2:
                    listarLibrosConsultados();
                    break;

                case 3:
                    listarAutoresConsultados();
                    break;

                case 4:
                    listarAutoresPorRangoDeAños();
                    break;

                case 5:
                    // Aquí tu lógica para listar libros por idioma
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
                continue; // Repetimos el ciclo hasta que se introduzca un título válido
            }

            try {
                // Buscar en la base de datos
                Optional<Libros> libroBD = librosRepository.findByNombreContainingIgnoreCase(titulo)
                        .stream()
                        .findFirst();

                if (libroBD.isPresent()) {
                    // Mostrar información del libro encontrado en la base de datos
                    System.out.println("----- LIBRO -----");
                    System.out.println("Título: " + libroBD.get().getNombre());
                    System.out.println("Autor: " + libroBD.get().getAutores().getNombre());
                    System.out.println("Idioma: " + libroBD.get().getLenguaje());
                    System.out.println("Número de descargas: " + libroBD.get().getDescargas());
                    System.out.println("-------------------------------");
                } else {
                    // Buscar en la API
                    String urlBusqueda = URL_BASE + "books/?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8);
                    String jsonRespuesta = consumoApi.obtenerDatos(urlBusqueda);

                    LibrosResponse response = conversor.obtenerDatos(jsonRespuesta, LibrosResponse.class);
                    response.getResults().stream().findFirst().ifPresent(libro -> {
                        Autores autor = null;
                        if (libro.autores() != null && !libro.autores().isEmpty()) {
                            // Buscar autor en la base de datos o guardarlo si no existe
                            autor = autoresRepository.findAutorByNombre(libro.autores().get(0).nombre())
                                    .orElseGet(() -> {
                                        Autores nuevoAutor = new Autores();
                                        nuevoAutor.setNombre(libro.autores().get(0).nombre());
                                        nuevoAutor.setFechaNacimiento(libro.autores().get(0).fechaNacimiento());
                                        nuevoAutor.setFechaFallecimiento(libro.autores().get(0).fechaFallecimiento());
                                        return autoresRepository.save(nuevoAutor); // Guardar el nuevo autor
                                    });
                        }
                        // Crear y guardar el libro en la base de datos
                        Libros nuevoLibro = new Libros();
                        nuevoLibro.setNombre(libro.libro());
                        nuevoLibro.setLenguaje(String.valueOf(libro.lenguaje()));
                        nuevoLibro.setDescargas(libro.descargas());
                        nuevoLibro.setAutores(autor); // Asociar el autor al libro
                        librosRepository.save(nuevoLibro);
                        System.out.println("----- LIBRO -----");
                        System.out.println("Título: " + nuevoLibro.getNombre());
                        System.out.println("Autor: " + nuevoLibro.getAutores().getNombre());
                        System.out.println("Idioma: " + nuevoLibro.getLenguaje());
                        System.out.println("Número de descargas: " + nuevoLibro.getDescargas());
                        System.out.println("-------------------------------");
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

    private void listarLibrosConsultados() {
        List<Libros> libros = librosRepository.findAll();
        if (libros.isEmpty()) {
            System.out.println("No hay libros registrados en la base de datos.");
            return;
        }

        System.out.println("----- LISTA DE LIBROS CONSULTADOS -----\n");
        libros.stream()
                .sorted(Comparator.comparing(Libros::getNombre))
                .forEach(libro -> {
                    System.out.println("-------------------------------");
                    System.out.println("Título: " + libro.getNombre());
                    System.out.println("Autor: " + (libro.getAutores() != null ? libro.getAutores().getNombre() : "Desconocido"));
                    System.out.println("Idioma: " + (libro.getLenguaje() != null ? libro.getLenguaje() : "N/A"));
                    System.out.println("Número de descargas: " + (libro.getDescargas() != null ? libro.getDescargas() : "N/A"));
                    System.out.println("-------------------------------");
                });
    }

    @Transactional
    protected void listarAutoresConsultados() {
        // Consultar todos los libros con sus autores usando el repositorio
        List<Libros> libros = librosRepository.findAllWithAutoresAndLibros();

        if (libros.isEmpty()) {
            System.out.println("No hay libros (y por ende autores) registrados en la base de datos.");
            return;
        }

        // Usar un Map para evitar duplicados y agrupar libros por autores
        Map<Autores, List<Libros>> autoresMap = libros.stream()
                .filter(libro -> libro.getAutores() != null) // Filtrar libros sin autor asignado
                .collect(Collectors.groupingBy(Libros::getAutores));

        if (autoresMap.isEmpty()) {
            System.out.println("No hay autores registrados en la base de datos.");
            return;
        }

        System.out.println("----- LISTA DE AUTORES CONSULTADOS -----\n");

        // Iterar sobre el Map y mostrar la información
        autoresMap.forEach((autor, librosDelAutor) -> {
            System.out.println("-------------------------------");
            System.out.println("Autor: " + autor.getNombre());
            System.out.println("Fecha de nacimiento: " + autor.getFechaNacimientoTexto());
            System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimientoTexto());

            if (librosDelAutor.isEmpty()) {
                System.out.println("Libros: Desconocido");
            } else {
                System.out.println("Libros:");
                librosDelAutor.forEach(libro -> System.out.println("- " + libro.getNombre()));
            }
            System.out.println("-------------------------------\n");
        });
    }

    private void listarAutoresPorRangoDeAños() {
        System.out.println("Ingrese el año vivo de autor(es) que desea buscar:");
        int anio = Integer.parseInt(sc.nextLine().trim());

        try {
            // Buscar autores vivos en el año ingresado
            List<Autores> autores = autoresRepository.findAutoresByRangoDeAnios(anio);

            if (autores.isEmpty()) {
                System.out.println("No se encontraron autores vivos en el año: " + anio);
                return;
            }

            System.out.println("----- AUTORES VIVOS EN EL AÑO " + anio + " -----");

            // Mostrar autores y sus libros
            autores.forEach(autor -> {
                System.out.println("-------------------------------");
                System.out.println("Autor: " + autor.getNombre());
                System.out.println("Fecha de nacimiento: " + autor.getFechaNacimientoTexto());
                System.out.println("Fecha de fallecimiento: " + autor.getFechaFallecimientoTexto());

                // Obtener libros del autor
                List<Libros> librosDelAutor = librosRepository.findLibrosByAutor(autor.getId());
                if (librosDelAutor.isEmpty()) {
                    System.out.println("Libros: Ninguno registrado");
                } else {
                    System.out.println("Libros:");
                    librosDelAutor.forEach(libro -> System.out.println("- " + libro.getNombre()));
                }
            });
            System.out.println("-------------------------------");
        } catch (Exception e) {
            System.out.println("Ocurrió un error al buscar autores: " + e.getMessage());
        }
    }







}
