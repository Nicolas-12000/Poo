package com.taller.pagos.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "pedidos")
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String numeroPedido;

    private LocalDateTime fechaCreacion;

    protected Pedido() {}

    /**
     * Constructor para crear un pedido con número.
     * Se registra la fecha de creación automáticamente.
     */
    public Pedido(String numeroPedido) {
        this.numeroPedido = numeroPedido;
        this.fechaCreacion = LocalDateTime.now();
    }

    public Long getId() { return id; }
    public String getNumeroPedido() { return numeroPedido; }
    public LocalDateTime getFechaCreacion() { return fechaCreacion; }

}
