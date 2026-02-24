Problemas de carrera y soluciones (resumen para estudiantes)

Qué es un problema de carrera
- Ocurre cuando dos o más procesos/hilos intentan leer/escribir el mismo dato concurrentemente, y el resultado depende del orden de ejecución.

Ejemplo en este proyecto
- Dos operadores o procesos intentan actualizar el mismo `Order` (p. ej. actualizar estado o asignar courier). Sin protección, la última escritura gana y se pierden datos intermedios.

Soluciones y trade-offs
1) Bloqueo optimista (implementado)
- Cómo funciona: cada entidad tiene un campo `@Version` (p. ej. `version` Long). Al hacer `save()`, Hibernate verifica que la versión no haya cambiado; si cambió, lanza `OptimisticLockException`.
- Ventaja: buena para entornos con pocas colisiones; no bloquea la DB.
- Desventaja: cuando hay colisiones frecuentes, hay que reintentar la operación.
- Implementación aquí: `@Version private Long version;` en `Order`.

2) Bloqueo pesimista
- Cómo funciona: `entityManager.lock(order, LockModeType.PESSIMISTIC_WRITE)` o usar métodos de repositorio con `@Lock(PESSIMISTIC_WRITE)`.
- Ventaja: evita colisiones porque bloquea la fila hasta terminar la transacción.
- Desventaja: reduce concurrencia y puede causar deadlocks si no se usa con cuidado.

3) Transacciones y aislamiento
- Use `@Transactional` en servicios que realizan múltiples operaciones DB.
- Ajuste de niveles de aislamiento (`READ_COMMITTED`, `REPEATABLE_READ`, `SERIALIZABLE`) según necesidad.

4) Colas / eventos (arquitectura avanzada)
- Para operaciones que cambian estado crítico (pagos, asignaciones), considerar procesarlas a través de una cola (RabbitMQ, Kafka) y un consumidor único para evitar contención.

Buenas prácticas
- Mantener transacciones cortas.
- Reintentar operaciones fallidas por `OptimisticLockException` con un número limitado de intentos exponencial.
- Registrar y monitorear excepciones de concurrencia para ajustar estrategia.

Qué implementé en el repo
- `@Version` en `Order` para bloqueo optimista.
- Documentación en `docs/concurrency.md` con pautas.

Sugerencia para continuar
- Implementar un reintento automático (ej. con Spring `@Retryable`) alrededor de operaciones críticas que fallen por `OptimisticLockException`.
- Añadir tests de concurrencia para validar el comportamiento.
