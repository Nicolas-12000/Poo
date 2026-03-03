package com.taller.pagos.service;

import com.taller.pagos.model.Pedido;
import com.taller.pagos.repository.PedidoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> listarPedidos() {
        return pedidoRepository.findAll();
    }

    public Pedido guardar(Pedido p) {
        return pedidoRepository.save(p);
    }

    Pedido buscarPorNumero(String numero) {
        return pedidoRepository.findByNumeroPedido(numero);
    }
    
    public void eliminar(Long id) {
        pedidoRepository.deleteById(id);
    }

    public Pedido buscarPorId(Long id) {
        return pedidoRepository.findById(id).orElse(null);
    }
    
    // Nota: la lógica de negocio sobre pedidos debe vivir en el servicio; el controlador solo delega.
}
