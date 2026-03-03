## Arquitectura y decisiones de diseño — explicación detallada para estudiantes

### Resumen
Arquitectura por capas (Controller → Service → Repository) simple y didáctica, diseñada para que se aprecien principios de POO y buenas prácticas sin introducir complejidad innecesaria.

### Decisiones principales (qué y por qué)
- Separación en capas: facilita pruebas y mantenimiento y reduce acoplamiento.
- `Service` como interfaz + implementación (`impl`): permite polimorfismo e inversión de dependencias (puedes mockear o sustituir la implementación en tests o para otros entornos).
- Mapeo DTO ↔ Entity explícito: evita acoplar la API a la estructura de la BD y permite controlar la serialización JSON.
- Uso de Spring Data JPA y PostgreSQL: herramientas estándar en la industria que simplifican persistencia.

### Principios de diseño aplicados con ejemplos concretos
- SOLID
  - Single Responsibility (SRP): cada clase tiene una única responsabilidad. Ejemplo: `BookController` sólo expone endpoints y delega; `BookServiceImpl` realiza la lógica de negocio.
    - Ver: `src/main/java/com/example/biblioteca/controller/BookController.java`
    - Ver: `src/main/java/com/example/biblioteca/service/impl/BookServiceImpl.java`

  - Open/Closed (OCP): los servicios usan interfaces (`BookService`). Para añadir comportamiento nuevo no modificas el contrato, puedes añadir una nueva implementación.
    - Ver: `src/main/java/com/example/biblioteca/service/BookService.java`

  - Liskov Substitution & Interface Segregation: las interfaces de servicio son pequeñas y enfocadas (no tienen métodos innecesarios).

  - Dependency Inversion: controladores dependen de abstracciones (interfaces) y no de implementaciones concretas.
    - Ejemplo: `BookController` declara `BookService service` en el constructor.

- KISS (Keep It Simple, Stupid): se evita sobre-ingeniería; por ejemplo no se introduce un patrón de repositorio complejo ni DDD innecesario.
- DRY (Don't Repeat Yourself): los mappers y servicios evitan duplicación de lógica.

### POO y tipos de polimorfismo presentes
- Polimorfismo por subtipado (inclusión): uso de interfaces para servicios. Ejemplo: `BookService` (interfaz) y `BookServiceImpl` (implementación).
- Polimorfismo por sobrecarga: no es relevante en esta API REST (no se usan múltiples métodos con el mismo nombre que varían en parámetros en las capas principales).
- Polimorfismo por delegación/composición: `Loan` compone (`has-a`) un `Book` y un `User`.

### Concurrencia y condición de carrera (caso de examen)
Un problema típico en sistemas de préstamo es que dos usuarios intenten prestar el mismo libro simultáneamente. Si no se controla, ambos podrían ver `available=true` y crear dos préstamos para el mismo recurso.

En este proyecto abordamos el problema con *optimistic locking* de JPA:
- Añadimos `@Version` en la entidad `Book` (campo `version`).
- El flujo es: cargar la entidad, comprobar `available`, marcar `available=false` y guardar.
- Si otra transacción hubiera modificado la misma fila entre la lectura y la escritura, JPA lanzará `OptimisticLockException` al intentar confirmar la transacción y el cambio fallará.

Ventajas de este enfoque:
- Menos bloqueos a nivel de BD (mejor para lectura concurrida).
- Sencillo de implementar y razonable para un sistema con normalmente pocas contenciones.

Alternativas:
- Pessimistic locking (`SELECT ... FOR UPDATE`) mediante `@Lock(PESSIMISTIC_WRITE)` en repositorios: garantiza exclusión pero puede generar más bloqueos.
- Operaciones atómicas en BD (UPDATE ... WHERE available = true) que retornan número de filas afectadas: buena opción cuando la lógica se puede expresar en una sola sentencia.

En la práctica para entrevistas debes poder explicar pros/cons y cuándo escoger cada técnica.

### Dónde está cada cosa (mapa rápido)
- Controllers: `src/main/java/com/example/biblioteca/controller`
- Services: `src/main/java/com/example/biblioteca/service` (+ `impl`)
- Repositories: `src/main/java/com/example/biblioteca/repository`
- Models: `src/main/java/com/example/biblioteca/model`
- DTOs: `src/main/java/com/example/biblioteca/dto`

### Cómo documentamos con Swagger / OpenAPI
- `springdoc-openapi` inspecciona controladores y modelos y genera automáticamente la spec OpenAPI en `/api-docs`.
- Para enriquecer la documentación debes usar anotaciones `@Operation` y `@Schema` en controladores/DTOs.
  - Ejemplo (añadir sobre un endpoint):

```java
@Operation(summary = "Prestar un libro", description = "Marca un libro como prestado y crea un registro de préstamo")
@PostMapping("/lend")
public ResponseEntity<Long> lend(@Valid @RequestBody LoanRequestDto req) { ... }
```

### Consideraciones de extensión y mejora (para examen o ampliación)
- Añadir `@ControllerAdvice` y una jerarquía de excepciones custom para respuestas de error uniformes.
- Añadir pruebas: unitarias para servicios (mockear repos), integración con `@SpringBootTest` y base de datos en memoria o Testcontainers.
- Añadir paginación y filtros para `GET /api/books`.

