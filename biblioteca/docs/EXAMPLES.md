# Ejemplos comentados y fragmentos (para estudiantes)

Este archivo contiene fragmentos de código y explicaciones que ayudan a entender cómo funciona el proyecto en la práctica.

1) `BookController` — responsabilidad y flujo

```java
@RestController
@RequestMapping("/api/books")
public class BookController {
    private final BookService service;

    public BookController(BookService service) { this.service = service; }

    @PostMapping
    public ResponseEntity<BookDto> create(@Valid @RequestBody BookCreateDto dto) {
        // 1) Validación automática por @Valid
        // 2) Mapear DTO -> Entity y guardar en servicio
        return ResponseEntity.ok(service.create(dto));
    }
}
```

2) `BookServiceImpl.create` — conversión y persistencia

```java
public BookDto create(BookCreateDto dto) {
    Book b = BookMapper.toEntity(dto); // Convertir DTO a entidad (control explicito)
    Book saved = repo.save(b);         // Persistir con JPA
    return BookMapper.toDto(saved);    // Convertir entidad a DTO de respuesta
}
```

3) `LoanServiceImpl.lend` — regla de negocio breve

```java
public Long lend(LoanRequestDto request) {
    Book book = bookRepo.findById(request.getBookId()).orElseThrow(...);
    if (!book.getAvailable()) throw new RuntimeException("Book not available");
    book.setAvailable(false);
    bookRepo.save(book);

    Loan loan = Loan.builder()
            .book(book)
            .user(user)
            .loanDate(LocalDateTime.now())
            .returned(false)
            .build();
    return loanRepo.save(loan).getId();
}
```

Explicación:
- Verás validaiones mínimas y cambios de estado en la entidad `Book` antes de crear el `Loan`.
- Esta lógica es clara para un examen: muestra control de estado, acceso a repos y creación de una entidad relacionada.

4) Ejemplo de documentación en Swagger con `@Operation`

```java
@Operation(summary = "Prestar libro", description = "Recibe userId y bookId, marca el libro como no disponible y crea el préstamo")
@PostMapping("/lend")
public ResponseEntity<Long> lend(@Valid @RequestBody LoanRequestDto req) { ... }
```

