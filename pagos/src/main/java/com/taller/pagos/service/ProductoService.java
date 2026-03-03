package com.taller.pagos.service;

import com.taller.pagos.model.Producto;
import com.taller.pagos.repository.ProductoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    public Producto guardar(Producto p) {
        return productoRepository.save(p);
    }

    public void eliminar(Long id) {
        productoRepository.deleteById(id);
    }

    public Producto buscarPorId(Long id) {
        return productoRepository.findById(id).orElse(null);
    }

    // Nota: el servicio encapsula acceso al repositorio para evitar que capas superiores manipulen colecciones internamente.

}
