# Biblioteca API

Proyecto de ejemplo en Spring Boot para una biblioteca: registro y gestión de libros y usuarios, préstamo y devolución de libros.

Contenido:
- API REST para CRUD de libros y usuarios.
- Endpoints para prestar y devolver libros.
- Documentación OpenAPI (Swagger).

Requisitos
- Java 21
- Maven
- PostgreSQL (o H2 si prefieres pruebas locales con configuración mínima)

Quick start
1. Copia `.env.example` a `.env` y ajusta las credenciales de BD.
2. Exporta variables de entorno (Linux/macOS):
```bash
export $(grep -v '^#' .env | xargs)
```
3. Ejecuta con Maven:
```bash
mvn spring-boot:run
```
4. Swagger UI: `http://localhost:8080/swagger-ui.html`

Archivos importantes
- [application.properties](src/main/resources/application.properties)
- [src/main/java/com/example/biblioteca](src/main/java/com/example/biblioteca)
- [ .env.example ](.env.example)

Documentación y decisiones de diseño en la carpeta `docs/`.
