package com.taller.pagos.config;

import com.taller.pagos.model.Pago;
import com.taller.pagos.model.Pedido;
import com.taller.pagos.model.Producto;
import com.taller.pagos.service.PagoService;
import com.taller.pagos.service.PedidoService;
import com.taller.pagos.service.ProductoService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;

@Configuration
public class DataLoader {

    @Bean
    CommandLineRunner init(PagoService pagoService, ProductoService productoService, PedidoService pedidoService) {
        return args -> {
            // Semilla de productos y pedidos para facilitar pruebas manuales.
            productoService.guardar(new Producto("Camiseta", new BigDecimal("19.99")));
            productoService.guardar(new Producto("Gorra", new BigDecimal("9.50")));

            pedidoService.guardar(new Pedido("ORD-1001"));
            pedidoService.guardar(new Pedido("ORD-1002"));

            // Pagos iniciales (se crean usando la API del servicio para respetar reglas de dominio)
            try {
                pagoService.registrarPago(new Pago(new BigDecimal("100.00"), "ORD-1001"));
                pagoService.registrarPago(new Pago(new BigDecimal("250.50"), "ORD-1002"));
            } catch (Exception ignored) {
                // Ignorar errores de semilla para que la app arranque sin bloquearse
            }
        };
    }

}
