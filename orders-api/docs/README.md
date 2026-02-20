Orders API — Documentación

Propósito
- Proyecto de ejemplo: API de pedidos/entregas con Spring Boot, PostgreSQL y Flyway.
- Diseño: MVC (Controller → Service → Repository) siguiendo principios SOLID y KISS.

Estructura principal
- `src/main/java/com/example/orders_api/model`: Entidades JPA (`Order`, `OrderItem`, `Product`, `Customer`, `Merchant`, `Courier`).
- `src/main/java/com/example/orders_api/repository`: Interfaces `JpaRepository` para persistencia.
- `src/main/java/com/example/orders_api/service`: Lógica de negocio en servicios (interfaces + impls).
- `src/main/java/com/example/orders_api/controller`: Endpoints REST.
- `src/main/resources/db/migration`: Migraciones Flyway (V1__init.sql).

Qué encontrarás aquí
- `architecture.md`: explicación de capas, anotaciones JPA/Spring y principios SOLID.
- `concurrency.md`: problemas de carrera, por qué usar bloqueo optimista y recomendaciones prácticas.
- `swagger.md`: por qué usamos Swagger/OpenAPI y cómo está configurado en el proyecto.

Resumen pedagógico: ¿qué debes entender para leer el código?
- Anotaciones JPA/Spring: ver `architecture.md` para una guía rápida. En pocas palabras: las anotaciones marcan a las clases/atributos para que Spring/Hibernate sepa cómo manejarlas (persistencia, inyección de dependencias, control de transacciones, auditoría).
- SOLID: las clases están separadas en capas y responsabilidades. Cada `Service` se encarga de reglas de negocio; los `Repository` solo de acceso a datos; los `Controller` exponen la API.
- BigDecimal: todos los importes usan `BigDecimal` para evitar errores de precisión con decimales.
- Flyway: versiona la base de datos. No uses `spring.jpa.hibernate.ddl-auto=update` en producción; deja que Flyway maneje el esquema.

Cómo usar
1. Configura `.env` con tus credenciales Postgres.
2. Construye y ejecuta:

```bash
./mvnw -DskipTests package
./mvnw spring-boot:run
```

- Swagger UI: `http://localhost:8080/docs`
- API JSON: `http://localhost:8080/v3/api-docs`

Próximos pasos recomendados
- Añadir DTOs y validación (pendiente).
- Escribir tests unitarios para `OrderPricingService` (pendiente).
