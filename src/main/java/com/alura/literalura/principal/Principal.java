package com.alura.literalura.principal;

import com.alura.literalura.model.Autores;
import com.alura.literalura.model.DatosLibros;
import com.alura.literalura.model.Libros;
import com.alura.literalura.service.ConsumoAPI;
import com.alura.literalura.service.ConvierteDatos;
import com.alura.literalura.service.LibrosResponse;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * @author Álvarez Galicia Raúl Alexander
 * Clase principal que muestra el menú principal, se hace uso del framework Spring,
 * consultas a la API de Gutendex, uso de PostgreSQL para la permanencia de datos
 * al consultar libros,
 */
public class Principal {
    private Scanner sc = new Scanner(System.in);
    private ConsumoAPI consumoApi = new ConsumoAPI();
    private final String URL_BASE = "https://gutendex.com/";
    private ConvierteDatos conversor = new ConvierteDatos();
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
            while (opcion != 0) {
                switch (opcion) {
                    case 1:
                        buscarLibroPorTitulo();
                        break;
                    case 2:

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
    }
    private void buscarLibroPorTitulo(){
        System.out.println("Ingrese el titulo del libro: ");
        var titulo = sc.nextLine();
        try {
            // Construir la URL para buscar el libro por título
            var urlBusqueda = URL_BASE + "books/?search=" + URLEncoder.encode(titulo, StandardCharsets.UTF_8) + "&languages=en";

            // Obtener datos JSON desde la API
            String jsonRespuesta = consumoApi.obtenerDatos(urlBusqueda);

            // Convertir JSON en una lista de objetos (debes mapear correctamente los resultados)
            LibrosResponse response = conversor.obtenerDatos(jsonRespuesta, LibrosResponse.class);

            // Verificar si hay resultados
            if (response.getResults().isEmpty()) {
                System.out.println("No se encontraron libros con el título: " + titulo);
                return;
            }

            // Mostrar los datos de los libros encontrados
            System.out.println("Libros encontrados:");
            response.getResults().forEach(libro -> System.out.println("Título: " + libro.libro()));

        } catch (Exception e) {
            System.out.println("Ocurrió un error al buscar el libro: " + e.getMessage());
        }






    }
}
