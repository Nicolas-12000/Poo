Swagger / OpenAPI — qué y por qué

Por qué usamos Swagger (springdoc)
- Swagger (OpenAPI) documenta automáticamente los endpoints REST y crea una UI interactiva para probarlos.
- Beneficios: documentación siempre actualizada, clientes y testers pueden explorar la API sin leer el código.

Cómo está configurado en este proyecto
- Dependencia: `springdoc-openapi-starter-webmvc-ui` en `pom.xml`.
- Ruta de la UI: `springdoc.swagger-ui.path=/docs` (ver `src/main/resources/application.properties`).
- JSON OpenAPI: disponible en `/v3/api-docs`.

Cómo usarlo en el código
- Anotar controladores con `@Tag` (categoría) para agrupar endpoints.
- Anotar métodos con `@Operation(summary = "...", description = "...")` para describir la operación.
- Añadir `@ApiResponse(responseCode = "200", description = "OK", content = @Content(schema = @Schema(implementation = Order.class)))` para documentar respuestas (usar DTOs para contratos claros).

Ejemplo (conceptual)
```
@Tag(name = "Orders", description = "Operaciones sobre pedidos")
@RestController
public class OrderController {
    @Operation(summary = "Crear pedido")
    @ApiResponse(responseCode = "201", description = "Pedido creado")
    public ResponseEntity<Order> create(@RequestBody OrderDto dto) { ... }
}
```

Recomendaciones para el repo
- Usar DTOs en las firmas de los controladores y mapear a entidades internamente. Documenta los DTOs con `@Schema`.
- Añadir ejemplos en `@ExampleObject` para que la UI muestre payloads de ejemplo.
- Añadir una clase `ErrorResponse` y referenciarla en `@ApiResponse` para documentar errores manejados por `GlobalExceptionHandler`.

Ver también: `src/main/resources/application.properties` y los controladores en `src/main/java/com/example/orders_api/controller`.
