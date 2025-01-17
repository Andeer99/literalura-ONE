
Bienvenido a **Librería Literalura**, proyecto del programa ONE de Oracle + Alura

## ¿Qué puedes hacer con este proyecto?

### Funcionalidades principales:
1. **Buscar libros por título:**
   Ingresa el título de un libro para encontrar información detallada sobre él, como su autor, idioma y número de descargas.

2. **Listar libros registrados:**
   Consulta todos los libros guardados en la base de datos local.

3. **Explorar autores registrados:**
   Visualiza una lista de todos los autores disponibles en la base de datos.

4. **Filtrar autores vivos en un año específico:**
   Descubre qué autores estaban vivos durante un año en particular y consulta los libros que escribieron.

5. **Buscar libros por idioma:**
   Encuentra libros en el idioma que prefieras, como español, inglés, francés o portugués.

## ¿Cómo funciona?

Este sistema combina dos fuentes principales de datos:
- **Base de datos local:** Donde se almacenan libros y autores registrados previamente.
- **API externa (Gutendex):** Proporciona datos adicionales de libros públicos disponibles en línea.

Cuando haces una búsqueda, primero se consulta la base de datos local. Si no se encuentra el libro, el sistema buscará automáticamente en la API externa y añadirá el libro a la base de datos para futuras consultas.

## Requisitos

Para ejecutar este proyecto necesitas:
1. **Java 17 o superior**
2. **Apache Maven**
3. **Base de datos MySQL**
4. **Conexión a internet** (para consultar la API externa)

## Cómo comenzar

1. Clona este repositorio en tu computadora.
2. Configura tu archivo `application.properties` con las credenciales de tu base de datos MySQL.
3. Ejecuta el proyecto usando tu IDE de preferencia o desde la línea de comandos con:
   ```
   mvn spring-boot:run
   ```
4. Sigue las instrucciones en la terminal para explorar todas las funcionalidades.

## ¿Quién puede usar este proyecto?

Este proyecto está pensado para:
- Lectores que quieren descubrir nuevos libros.
- Programadores interesados en aprender sobre Spring Boot, bases de datos y consumo de APIs externas.
- Estudiantes que deseen practicar habilidades de programación y desarrollo de sistemas.

## ¡Contribuye!

Si tienes ideas para mejorar este proyecto, no dudes en contribuir. Puedes realizar un fork del repositorio, hacer tus modificaciones y enviar un pull request. Toda colaboración es bienvenida.

---

¡Gracias por usar Librería Literalura! Esperamos que disfrutes explorando el mundo de los libros tanto como nosotros disfrutamos desarrollando este proyecto.
