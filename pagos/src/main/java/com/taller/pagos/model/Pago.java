package com.taller.pagos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "pagos")
public class Pago {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Monto del pago. Se mantiene private y sin setter público para evitar manipulaciones externas.
    private BigDecimal monto;

    // Fecha del evento de pago; se actualiza al procesar.
    private LocalDateTime fecha;

    // Estado del pago (PENDIENTE, PROCESADO, RECHAZADO). No tiene setter público.
    @Enumerated(EnumType.STRING)
    private EstadoPago estado;

    // Identificador del pedido asociado. Se guarda como referencia simple (String) para mantener el foco del taller.
    private String pedidoId;

    // Constructor protegido requerido por JPA
    protected Pago() {
    }

    /**
     * Constructor de dominio para crear un Pago nuevo. El id lo gestiona JPA.
     * @param monto monto del pago
     * @param pedidoId identificador del pedido asociado
     */
    public Pago(BigDecimal monto, String pedidoId) {
        this.monto = monto;
        this.pedidoId = pedidoId;
        this.fecha = LocalDateTime.now();
        this.estado = EstadoPago.PENDIENTE;
    }

    // Getters: exponen estado de forma segura sin permitir mutación directa.
    public Long getId() { return id; }
    public BigDecimal getMonto() { return monto; }
    public LocalDateTime getFecha() { return fecha; }
    public EstadoPago getEstado() { return estado; }
    public String getPedidoId() { return pedidoId; }

    /**
     * Procesa el pago: valida el monto (mayor que cero) y cambia internamente el estado a PROCESADO.
     * Si la validación falla, marca RECHAZADO y lanza excepción.
     */
    public void procesarPago() {
        if (!validarMonto()) {
            this.estado = EstadoPago.RECHAZADO;
            throw new IllegalStateException("Monto inválido. No se puede procesar el pago.");
        }
        this.fecha = LocalDateTime.now();
        this.estado = EstadoPago.PROCESADO;
    }

    /**
     * Valida que el monto sea mayor que cero.
     * @return true si monto válido
     */
    public boolean validarMonto() {
        return this.monto != null && this.monto.compareTo(BigDecimal.ZERO) > 0;
    }

    // Nota: no se exponen setters para `monto` ni `estado` para garantizar encapsulamiento.

}
