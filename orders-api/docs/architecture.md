Arquitectura del proyecto

Capas
- Controller: expone la API REST, convierte peticiones HTTP a llamadas de servicio.
- Service: lógica de negocio; clases con interfaz + implementación para facilitar pruebas y separación de responsabilidades.
- Repository: interfaces que extienden `JpaRepository` para acceso a datos.
- Model (entities): mapeo JPA de la base de datos.

Flujo típico al crear un pedido
1. `OrderController` recibe `POST /orders` con payload.
2. Valida entrada (pendiente: DTOs + validación).
3. Llama a `OrderService.create(order)`.
4. `OrderService` prepara `OrderItem` (setea `unitPrice` a partir de `Product`) y delega cálculo a `OrderPricingService`.
5. `OrderPricingService` calcula `subtotal`, aplica descuentos/iva y devuelve `total` como `BigDecimal`.
6. `OrderRepository` persiste la entidad; Flyway mantiene el esquema en el DB.

Anotaciones importantes (rápido resumen para estudiantes)
- `@Entity` / `@Table`: marca una clase como entidad persistente y define la tabla SQL.
- `@Id`, `@GeneratedValue`: clave primaria y estrategia de generación.
- `@ManyToOne`, `@OneToMany`, `@JoinColumn`, `mappedBy`: relaciones entre entidades; `mappedBy` indica el lado inverso.
- `@Transactional`: delimita una transacción; útil en servicios que realizan varias lecturas/escrituras.
- `@Service`, `@Repository`, `@Controller` / `@RestController`: estereotipos de Spring para inyección de dependencias y semántica de capa.
- `@CreatedDate`, `@LastModifiedDate`: auditoría automática (con `@EnableJpaAuditing`).
- `@Version`: campo para bloqueo optimista (control de concurrencia basado en versiones).
- Lombok (`@Data`, `@NoArgsConstructor`, `@AllArgsConstructor`): genera getters/setters/constructores para acortar boilerplate.

¿Por qué estas anotaciones?
- Separan responsabilidades entre framework y tu lógica: anotar una clase hace que Spring/Hibernate realicen trabajo repetitivo (mapear, inyectar, abrir transacciones).
- Facilitan pruebas: usar `@Service` y una interfaz permite mockear implementaciones en tests.

SOLID (resumen aplicado al proyecto)
- S (Single Responsibility): cada clase tiene una única responsabilidad — `OrderService` gestiona órdenes, `OrderPricingService` solo calcula precios.
- O (Open/Closed): servicios pueden extenderse (nuevas reglas de pricing) sin modificar su API pública.
- L (Liskov): las implementaciones concretas respetan las interfaces definidas.
- I (Interface Segregation): interfaces pequeñas y específicas (ej. servicios separados para búsqueda y pricing).
- D (Dependency Inversion): los controladores dependen de interfaces (`OrderService`) y no de implementaciones.

Decisiones importantes
- BigDecimal: usado para todos los importes (evita errores de punto flotante).
- Flyway: migraciones versionadas y reproducibles en `src/main/resources/db/migration`.
- Auditing: `@CreatedDate` y `@LastModifiedDate` habilitados con `@EnableJpaAuditing`.
- Locking: se añadió `@Version` en `Order` para bloqueo optimista y evitar condiciones de carrera.

Recomendaciones
- Añadir DTOs para separar modelo persistente de contrato de API.
- Configurar `spring.jpa.hibernate.ddl-auto=none` fuera de perfiles de desarrollo, y dejar que Flyway aplique cambios.
