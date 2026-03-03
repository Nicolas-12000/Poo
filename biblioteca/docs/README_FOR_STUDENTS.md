## Guía para estudiantes — por qué y cómo está hecho el proyecto

Esta guía amplía conceptos y explica el "por qué" detrás de las decisiones del código. Está pensada para alguien que conoce lo básico de Java/Spring y quiere entender cómo se aplican prácticas de POO y buen diseño.

1) Capas y responsabilidades (qué, dónde y por qué)
	- Dónde: `src/main/java/com/example/biblioteca/controller`.
	- Por qué: actúan como frontera del sistema; deben ser delgadas y delegar la lógica a servicios.
	- Dónde: `src/main/java/com/example/biblioteca/service` y `service/impl`.
	- Por qué: aislar la lógica facilita pruebas unitarias y permite cambiar implementaciones (polimorfismo).
	- Dónde: `src/main/java/com/example/biblioteca/repository`.
	- Por qué: delegar consultas y persistencia al framework, evitando SQL manual.
	- Dónde: `src/main/java/com/example/biblioteca/model`.

2) Validación de entrada

3) DTOs vs Entities — por qué usar DTOs

4) Lombok — por qué lo usamos

5) Mapeo entre DTO y Entity

6) Endpoints y contratos (qué recibe, qué devuelve)

7) OpenAPI / Swagger — cómo se documenta

8) Buenas prácticas y recomendaciones

9) Ejemplo comentado (ver `BookController` y `BookServiceImpl`)

Controller (simple y explicativo):

```java
@RestController
@RequestMapping("/api/books")
public class BookController {
		private final BookService service; // inyección por constructor

		@PostMapping
		public ResponseEntity<BookDto> create(@Valid @RequestBody BookCreateDto dto) {
				// La validación se hace antes de entrar al servicio.
				return ResponseEntity.ok(service.create(dto));
		}
}
```

ServiceImpl (lógica de negocio y persistencia delegada a repo):

```java
@Service
public class BookServiceImpl implements BookService {
		private final BookRepository repo;

		public BookServiceImpl(BookRepository repo) { this.repo = repo; }

		@Override
		public BookDto create(BookCreateDto dto) {
				Book b = BookMapper.toEntity(dto); // conversion explícita
				return BookMapper.toDto(repo.save(b));
		}
}
```

Explicación:

### Preguntas y respuestas rápidas para entrevistas (prep)

- Q: ¿Cuál es la diferencia entre `Service`, `Repository` y `Entity`?
  - `Entity`: representación de la tabla en la BD; objetos que contienen estado persistente. (ej: `Book` en `model/`).
  - `Repository`: interfaz para acceder a datos; delega consultas a Spring Data JPA (`BookRepository`).
  - `Service`: contiene la lógica de negocio, orquesta repositorios y transforma datos; no debe conocer detalles HTTP.

- Q: ¿Qué hace `@RestController` bajo el capó?
  - Es una composición de `@Controller` y `@ResponseBody`. Spring detecta la clase como bean y registra handlers para los `@RequestMapping`. El resultado de los métodos se serializa a JSON por el `HttpMessageConverter` configurado (ej: Jackson).

- Q: ¿Qué hace `@Entity` y por qué es importante `@Id`?
  - `@Entity` marca la clase para que JPA la mapee a una tabla. `@Id` identifica la primary key usada por el proveedor JPA (Hibernate) para seguimiento de entidades, sincronización del contexto y operaciones CRUD.

- Q: ¿Qué es `@Transactional` y por qué lo usamos en `LoanServiceImpl`?
  - `@Transactional` abre una transacción. Garantiza que las operaciones dentro del método se apliquen de forma atómica (commit/rollback). En `lend` queremos que la verificación, el cambio de estado del libro y la creación de `Loan` se traten como una unidad.

- Q: ¿Qué es `@Version` y cómo ayuda?
  - `@Version` habilita optimistic locking: Hibernate incrementa el campo versión en cada update. Si dos transacciones actualizan la misma fila simultáneamente, la que cometa con un `version` desactualizado fallará con `OptimisticLockException`.


