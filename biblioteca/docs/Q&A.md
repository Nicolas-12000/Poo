# Q&A — Preguntas y respuestas (detallado)

Este archivo amplía preguntas técnicas que te pueden pedir en una entrevista, explica decisiones del código y muestra fragmentos y comandos prácticos para entender y probar el proyecto.

1) ¿Cuál es la responsabilidad de `Controller`, `Service` y `Repository`?
- Controller (capa web): recibe solicitudes HTTP, valida entradas (`@Valid`), transforma request → DTO y delega al `Service`. Debe ser delgada: no contener lógica de negocio. Ejemplo: src/main/java/com/example/biblioteca/controller/BookController.java.
- Service (capa de negocio): contiene reglas y orquesta repositorios, maneja transacciones (`@Transactional`) y validaciones de negocio (ej: comprobar disponibilidad antes de crear un `Loan`). Ejemplo: src/main/java/com/example/biblioteca/service/impl/LoanServiceImpl.java.
- Repository (capa de persistencia): interfaces que extienden `JpaRepository`; delegan consultas a Spring Data JPA y Hibernate. Evitan SQL manual en la mayoría de los casos. Ejemplo: src/main/java/com/example/biblioteca/repository/BookRepository.java.

Consejo para entrevistas: explica la separación de responsabilidades y por qué cada capa facilita pruebas unitarias e integración.

2) ¿Por qué usamos DTOs y mappers en lugar de exponer entidades?
- Seguridad y encapsulación: las entidades pueden contener campos internos (IDs, versiones, relaciones) que no quieres exponer.
- Flexibilidad: los DTOs permiten evolucionar la API sin tocar la estructura de la BD.
- Rendimiento y serialización: evitas problemas de serialización como ciclos bidireccionales.

Ejemplo: `BookCreateDto` vs `Book` (mira `src/main/java/com/example/biblioteca/mapper/BookMapper.java`). En entrevistas, muestra cómo un cambio en la entidad no rompe la API si usas DTOs.

3) ¿Qué hace `@RestController` y `@RequestBody` bajo el capó?
- `@RestController` es `@Controller + @ResponseBody`. Spring registra los métodos anotados como manejadores HTTP.
- `@RequestBody` usa `HttpMessageConverter` (Jackson) para deserializar JSON en objetos Java. Las validaciones con `@Valid` se ejecutan tras la deserialización.

Fragmento relevante: `public ResponseEntity<BookDto> create(@Valid @RequestBody BookCreateDto dto)` en `src/main/java/com/example/biblioteca/controller/BookController.java`.

4) ¿Cómo se valida la entrada de la API y cómo devolver errores útiles?
- Declarativo: anotaciones (`@NotBlank`, `@NotNull`, `@Email`) en DTOs.
- En controladores: `@Valid` activa la validación.
- En `GlobalExceptionHandler` convertimos `MethodArgumentNotValidException` en un JSON con campo→mensaje para facilitar debugging a clientes.

Ejemplo de respuesta de validación (JSON):
```
{ "errors": { "title": "must not be blank" } }
```

5) ¿Qué hace `@Transactional` y por qué está en `LoanServiceImpl`?
- `@Transactional` define un contexto transaccional. Si una excepción no controlada ocurre, se hace rollback.
- En `lend` necesitamos que: leer libro, marcar como no disponible y crear el `Loan` sean atómicos. Si algo falla, revertimos todos los cambios para mantener consistencia.

Tip de entrevista: menciona el aislamiento de transacciones y cómo elegir políticas (READ_COMMITTED por defecto en muchas DBs).

6) ¿Qué es `@Version` y para qué lo usamos?
- `@Version` indica a JPA que mantenga un contador de versión. En cada UPDATE, Hibernate incrementa este campo y verifica que la versión en la BD coincide con la versión leída.
- Si otra transacción actualizó la fila, la versión cambió y Hibernate lanza `OptimisticLockException` al intentar guardar.

Uso práctico: evita condiciones de carrera en escenarios donde la contención es moderada y se prefiere evitar bloqueos pesimistas.

7) ¿Cómo resolvemos la condición de carrera al prestar un libro — y alternativas?
- Implementación actual: optimistic locking (`@Version`) + `@Transactional`.
- Flujo: leer entidad -> comprobar `available` -> marcar `available=false` -> save.

Alternativas y tradeoffs:
- Pessimistic locking (`FOR UPDATE`): garantiza exclusión pero reduce concurrencia y puede generar deadlocks.
- UPDATE atómico SQL: `UPDATE books SET available = false WHERE id = :id AND available = true` — devuelve filas afectadas; es eficiente pero mueve lógica a SQL.
- Cola de procesamiento: serializa operaciones críticas (útil en microservicios con alta carga).

En entrevistas, explica por qué escogiste optimistic locking (simplicidad, menor bloqueo) y cuándo cambiar a otras estrategias.

8) ¿Qué excepciones custom hay y cómo se exponen al cliente?
- `NotFoundException` → manejado por `GlobalExceptionHandler` y convertido en 404 con `{ "error": "..." }`.
- `ConflictException` → 409 cuando hay conflicto de estado (ej: libro no disponible).

Consejo: en entrevistas, justifica por qué usar excepciones específicas en vez de `RuntimeException` (mejor semántica, manejo centralizado).

9) ¿Cómo se documenta la API con Swagger/OpenAPI y cómo mejorar la spec?
- SpringDoc inspecciona controladores y modelos y publica `/api-docs` + `/swagger-ui.html`.
- Mejoras: `@Operation` en métodos para describir comportamiento (ej: efectos secundarios del endpoint `lend`) y `@Schema` en DTOs para ejemplos y descripciones.

Ejemplo:
```java
@Operation(summary = "Prestar un libro", description = "Marca libro como no disponible y crea Loan. Retorna 409 si no disponible.")
@PostMapping("/lend")
public ResponseEntity<Long> lend(@Valid @RequestBody LoanRequestDto req) { ... }
```

10) ¿Qué significa `requiredMode = Schema.RequiredMode.REQUIRED` y por qué se cambió?
- `required` fue deprecado. `requiredMode` es la forma moderna de declarar que un campo es requerido en la spec OpenAPI. Esto no obliga en tiempo de ejecución (la validación la hace `@NotNull`/`@NotBlank`).

11) ¿Cómo preparar el entorno y probar endpoints manualmente?
- Copia `.env.example` → `.env`, exporta variables y ejecuta la app.

Comandos útiles:
```bash
cp .env.example .env
export $(grep -v '^#' .env | xargs)
mvn spring-boot:run
# Swagger UI: http://localhost:8080/swagger-ui.html
```

Pruebas rápidas con curl:
```bash
curl -X POST -H "Content-Type: application/json" -d '{"title":"X"}' http://localhost:8080/api/books
```

12) ¿Qué pruebas deberías escribir y ejemplos rápidos?
- Unit tests (Mockito): mockear `BookRepository` y verificar que `BookServiceImpl.create` llama a `repo.save` con la entidad correcta.
- Prueba de `LoanServiceImpl`: simular libro disponible y comprobar que `loanRepo.save` es invocado y `book.available` queda en `false`.
- Integration tests: `@SpringBootTest` con Testcontainers (Postgres) para validar flujos end-to-end.

Ejemplo de test unitario (esquema):
```java
@ExtendWith(MockitoExtension.class)
class BookServiceTest {
  @Mock BookRepository repo;
  @InjectMocks BookServiceImpl service;

  @Test void create_calls_save() {
    var dto = new BookCreateDto(); dto.setTitle("T");
    service.create(dto);
    verify(repo).save(any(Book.class));
  }
}
```

13) ¿Cómo añadir paginación y filtros?
- Cambia `BookRepository` para regresar `Page<Book>` y en `BookController` acepta `Pageable` o parámetros `page`/`size`.
- Mapear `Page<Book>` → `Page<BookDto>` o construir un DTO de respuesta con items + metadata.

14) ¿Por qué se usan interfaces para servicios y ventajas prácticas?
- Facilita testing (puedes mockear la interfaz), permite introducir decoradores (caching, logging) o distintas implementaciones (sync/async) sin cambiar consumidores.

15) ¿Dónde están los casos de negocio más interesantes y por qué importan?
- `LoanServiceImpl`: gestión de estados (available/returned), transaccionalidad y manejo de conflictos—es donde se demuestra diseño y manejo de invariantes de negocio.

16) ¿Cómo manejar concurrencia fuerte (alta contención) en producción?
- Opciones:
  - Pessimistic locking: `@Lock(PESSIMISTIC_WRITE)` en repositorio.
  - Atomic DB update: `UPDATE ... WHERE available = true` y comprobar filas afectadas.
  - External queuing (RabbitMQ/Kafka) para serializar cambios de estado.

17) Patrones y paradigmas usados en el proyecto (con ejemplos de código)
- Layered Architecture: separación en paquetes `controller`, `service`, `repository`.
- Repository Pattern: `BookRepository extends JpaRepository<Book, Long>`.
- DTO Pattern: `BookDto`, `BookCreateDto`.

18) Decisiones a justificar en entrevistas (guía rápida)
- DTOs para desacoplar API ↔ BD. `@Transactional` para atomicidad. `@Version` para optimistic locking. Mappers explícitos para claridad. `ControllerAdvice` para manejo consistente de errores.

19) Documentar comportamientos especiales en Swagger (ejemplo extendido)
- Para el endpoint `lend` añade `@Operation` y documenta códigos HTTP esperados (200, 404, 409). Añade `@ApiResponse` si quieres mostrar esquemas para errores personalizados.

