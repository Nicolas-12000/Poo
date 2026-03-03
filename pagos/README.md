# Módulo de Pagos - Taller Semana 5

Proyecto Spring Boot pequeño para el taller sobre modificadores de acceso y encapsulamiento.

Características principales:
- `model`, `service`, `controller` en paquetes separados.
- `Pago` encapsulado: atributos `private`, sin setters públicos para `monto` ni `estado`.
- Lógica de negocio (`validarMonto`, `procesarPago`) dentro de la entidad `Pago`.
- `PagoService` orquesta persistencia y evita accesos directos a listas internas.

Estructura de endpoints disponibles:

- POST /pagos  -> Crear pago (Request body: `monto`, `pedidoId`)
- GET  /pagos  -> Listar pagos
- GET  /productos -> Listar productos
- GET  /pedidos   -> Listar pedidos

Ejecutar:

```bash
mvn -DskipTests package
mvn spring-boot:run
```

Variables de entorno (opcional `.env`):
- `DB_URL`, `DB_USER`, `DB_PASS`, `DB_DRIVER`, `JPA_DDL_AUTO`.

¿Necesito `java-dotenv`?
- No es estrictamente necesario. Spring Boot ya lee variables de entorno del sistema. La dependencia `java-dotenv` añade la conveniencia de cargar un archivo `.env` en entornos locales sin tener que exportar las variables manualmente. Si prefieres no añadir dependencias extras, simplemente exporta las variables en tu shell o configura tu IDE/contener.

Postman (colección simple): `postman_collection.json` incluido en el repo. Importa en Postman y prueba los endpoints.

Notas sobre diseño y SOLID:
- SRP: cada capa tiene responsabilidad única.
- DIP: servicios dependen de repositorios (abstracción).
- El foco del taller (modificadores y encapsulamiento) se mantiene: `Pago` protege `monto` y `estado` y expone `procesarPago()`.

Siguientes pasos posibles:
- Añadir integración completa con `Order` en el flujo de pagos.
- Agregar pruebas unitarias para `PagoService` y `PagoController`.
- Mejorar la API (DTOs de salida, mapeo con `ModelMapper` o similar).

