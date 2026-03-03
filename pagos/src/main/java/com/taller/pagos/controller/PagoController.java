package com.taller.pagos.controller;

import com.taller.pagos.model.Pago;
import com.taller.pagos.service.PagoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pagos")
public class PagoController {

    private final PagoService pagoService;

    public PagoController(PagoService pagoService) {
        this.pagoService = pagoService;
    }

    @PostMapping
    public ResponseEntity<Pago> crearPago(@RequestBody PagoRequest req) {
        // Construye DTO -> dominio. El controller no contiene lógica de negocio.
        Pago p = new Pago(req.getMonto(), req.getPedidoId());
        Pago saved = pagoService.registrarPago(p);
        // Devuelve la entidad persistida con id y estado actualizado
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @GetMapping
    public List<Pago> listarPagos() {
        return pagoService.listarPagos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Pago> obtener(@PathVariable Long id) {
        Pago p = pagoService.obtenerPorId(id);
        if (p == null) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(p);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        pagoService.eliminar(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/procesar")
    public ResponseEntity<Pago> procesar(@PathVariable Long id) {
        try {
            Pago p = pagoService.procesarPagoPorId(id);
            return ResponseEntity.ok(p);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.notFound().build();
        } catch (IllegalStateException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    // Simple DTO for incoming requests. Kept minimal to avoid exposing domain setters.
    public static class PagoRequest {
        private BigDecimal monto;
        private String pedidoId;

        public PagoRequest() {}

        public BigDecimal getMonto() {
            return monto;
        }

        public void setMonto(BigDecimal monto) {
            this.monto = monto;
        }

        public String getPedidoId() {
            return pedidoId;
        }

        public void setPedidoId(String pedidoId) {
            this.pedidoId = pedidoId;
        }
    }

}
