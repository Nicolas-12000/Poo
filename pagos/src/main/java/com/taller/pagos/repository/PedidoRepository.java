package com.taller.pagos.repository;

import com.taller.pagos.model.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
    Pedido findByNumeroPedido(String numeroPedido);
}
