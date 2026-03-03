package com.taller.pagos.service;

import com.taller.pagos.model.Pago;
import com.taller.pagos.repository.PagoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PagoService {

    private final PagoRepository pagoRepository;

    public PagoService(PagoRepository pagoRepository) {
        this.pagoRepository = pagoRepository;
    }

    /**
     * Registra un pago en el repositorio. No confía en campos mutables del objeto recibido;
     * crea una nueva entidad interna y delega el procesamiento a la entidad.
     * @param p objeto Pago con los datos recibidos (monto, pedidoId)
     * @return entidad persistida
     */
    public Pago registrarPago(Pago p) {
        if (p == null) {
            throw new IllegalArgumentException("Pago no puede ser null");
        }
        if (!p.validarMonto()) {
            throw new IllegalArgumentException("Monto debe ser mayor que cero");
        }
        // Create independent entity to avoid trusting caller-provided id/state
        // Se crea una entidad independiente para no confiar en id/estado externos
        Pago internal = new Pago(p.getMonto(), p.getPedidoId());
        internal.procesarPago();
        return pagoRepository.save(internal);
    }

    public List<Pago> listarPagos() {
        /**
         * Lista todos los pagos persistidos.
         */
        return pagoRepository.findAll();
    }

    // Package-private lookup
    Pago buscarPorId(Long id) {
        // Búsqueda accesible sólo dentro del paquete (encapsulamiento): usada por otras clases del servicio
        return pagoRepository.findById(id).orElse(null);
    }

    public Pago obtenerPorId(Long id) {
        return pagoRepository.findById(id).orElse(null);
    }

    public void eliminar(Long id) {
        pagoRepository.deleteById(id);
    }

    public Pago procesarPagoPorId(Long id) {
        Pago p = pagoRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Pago no encontrado"));
        p.procesarPago();
        return pagoRepository.save(p);
    }

}
