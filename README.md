
# Proyecto de Geolocalización y Autenticación con Spring Boot

[![Java](https://img.shields.io/badge/Java-22-blue.svg)](https://www.oracle.com/java/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4-green.svg)](https://spring.io/projects/spring-boot)
[![Google Maps API](https://img.shields.io/badge/Google%20Maps%20API-Enabled-yellow)](https://developers.google.com/maps)
[![Swagger](https://img.shields.io/badge/Swagger-API%20Docs-orange)](https://swagger.io/)

## Descripción del Proyecto

Este proyecto es una solución de **geolocalización y autenticación** desarrollada en **Java 22** utilizando **Spring Boot 4**. Su objetivo principal es permitir la captura, almacenamiento y actualización automática de coordenadas geográficas de personas, además de proporcionar una robusta gestión de usuarios con autenticación segura a través de JWT (JSON Web Tokens).

La aplicación también implementa una integración con la **API de Google Maps** para visualizar en tiempo real la ubicación de las personas, y utiliza **Swagger UI** para la documentación interactiva de los endpoints. Todo esto es parte de un entorno ágil y eficiente para proyectos que requieran una gestión avanzada de datos geoespaciales.

## Funcionalidades Principales

- **Autenticación JWT**: Seguridad implementada con tokens JWT para la autenticación de usuarios.
- **Integración con Google Maps API**: Visualización de coordenadas geográficas en mapas interactivos.
- **CRUD de Usuarios y Coordenadas**: Permite gestionar personas y sus ubicaciones geográficas, con soporte para almacenamiento y consultas eficientes.
- **Documentación API con Swagger**: Endpoints documentados automáticamente para facilitar la integración y pruebas de terceros.
- **Actualización automática**: Las coordenadas geográficas se actualizan cada 3 minutos, permitiendo mantener la información al día.
- **Seguridad y control de acceso**: Implementación de roles y permisos para garantizar que solo los usuarios autorizados puedan acceder a ciertos recursos.

## Arquitectura

La arquitectura del proyecto sigue el patrón **MVC (Modelo-Vista-Controlador)** y está completamente desacoplada entre el backend y el frontend, permitiendo un desarrollo modular y mantenible.

### Tecnologías Utilizadas

- **Java 22**: Última versión de Java, con mejoras en rendimiento y seguridad.
- **Spring Boot 4**: Framework robusto para crear aplicaciones empresariales de alto rendimiento.
- **MySQL**: Base de datos relacional para almacenamiento persistente de usuarios y coordenadas.
- **Google Maps API**: API para la representación de mapas interactivos.
- **JWT (JSON Web Tokens)**: Manejo de sesiones y seguridad.
- **Swagger UI**: Herramienta para la visualización y prueba de APIs.

## Instalación y Configuración

1. Clona este repositorio:
   ```bash
   git clone https://github.com/Sankz1/Proyecto.git
   ```
2. Configura las variables de entorno:
   - `GOOGLE_MAPS_API_KEY`: Tu clave de API de Google Maps.
   - `BEARER_TOKEN`: Token para autenticación JWT.
   
3. Ejecuta el proyecto con Maven:
   ```bash
   mvn spring-boot:run
   ```

4. Accede a la documentación de la API en:
   ```
   http://localhost:8080/swagger-ui/
   ```

## Endpoints Principales

- **/usuarios**: Gestión de usuarios, autenticación y detalles.
- **/coordenadas**: Consulta y actualización de coordenadas geográficas.
- **/auth/login**: Endpoint para autenticación y obtención de tokens JWT.


## Licencia

Este proyecto está bajo la licencia MIT - ver el archivo [LICENSE](LICENSE) para más detalles.

## Contacto

Para consultas, no dudes en contactarme:

- **Email**: e.devia.b1306@gmail.com
- **LinkedIn**: [Edwin Devia](https://www.linkedin.com/in/edwin-devia-20a5232b3/)
