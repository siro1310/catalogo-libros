# 📚 Catálogo de Libros — Aplicación de Consola

Aplicación de consola desarrollada en **Java con Spring Boot**, que permite gestionar un catálogo de libros utilizando **Spring Data JPA** y **PostgreSQL** como base de datos.

Este proyecto está diseñado con una arquitectura limpia y orientada a buenas prácticas, ideal como proyecto académico o de portafolio profesional.

---

## 🚀 Funcionalidades

La aplicación ofrece un menú interactivo por consola con las siguientes opciones:

1️⃣ **Buscar libro por título**  
- Muestra:
  - Título
  - Autor (Apellido, Nombre)
  - Idioma (ES, EN, FR, PT, etc.)
  - Número de descargas  
- Si no existe el libro, muestra un mensaje claro de *libro no encontrado*.

2️⃣ **Listar todos los libros**  
- Muestra todos los libros registrados en la base de datos.

3️⃣ **Listar autores registrados**  
- Muestra:
  - Autor (Apellido, Nombre)
  - Año de nacimiento
  - Año de fallecimiento (si aplica)
  - Lista de libros escritos por el autor (sin repetir el nombre).

4️⃣ **Listar autores vivos en un año determinado**  
- Permite consultar qué autores estaban vivos en un año específico.

5️⃣ **Listar libros por idioma**  
- El usuario ingresa el código del idioma (ES, EN, FR, PT, etc.).
- Muestra:
  - Título
  - Autor
  - Idioma
  - Número de descargas.

0️⃣ **Salir de la aplicación**  
- Cierre elegante del programa.

---

## 🧱 Arquitectura del Proyecto

El proyecto sigue una estructura clara y desacoplada:



---

## 🛠️ Tecnologías Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- PostgreSQL
- Maven
- Hibernate
- IntelliJ IDEA
- Git & GitHub

---

## 🗄️ Base de Datos

- Motor: **PostgreSQL**
- Las tablas se generan automáticamente con Hibernate.
- Incluye carga inicial de datos (autores y libros).

---

## ▶️ Ejecución del Proyecto

1. Crear la base de datos en PostgreSQL:
   ```sql
   CREATE DATABASE catalogo_libros;

2. ejecutar la aplicacion
  mvn spring-boot:run

