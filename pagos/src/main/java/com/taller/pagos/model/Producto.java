package com.taller.pagos.model;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Nombre del producto
    private String nombre;

    // Precio unitario
    private BigDecimal precio;

    protected Producto() {}

    /**
     * Constructor de dominio para Producto.
     * Mantiene atributos privados y sólo provee getters para respetar encapsulamiento.
     */
    public Producto(String nombre, BigDecimal precio) {
        this.nombre = nombre;
        this.precio = precio;
    }

    public Long getId() { return id; }
    public String getNombre() { return nombre; }
    public BigDecimal getPrecio() { return precio; }

}
