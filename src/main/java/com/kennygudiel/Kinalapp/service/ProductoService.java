package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Producto;
import com.kennygudiel.Kinalapp.repository.ProductoRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional

public class ProductoService implements IProductoService {

    private final ProductoRepository productoRepository;

    public ProductoService(ProductoRepository productoRepository) {
        this.productoRepository = productoRepository;
    }

    @Override
    public List<Producto> listarProductos() {
        return productoRepository.findAll();
    }

    @Override
    public Producto guardar(Producto producto) {
        validarProducto(producto);
        return productoRepository.save(producto);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Producto> buscarPorId(Integer id) {
        return productoRepository.findById(id);
    }

    @Override
    public Producto actualizar(Integer id, Producto producto) {

        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no se encontró con ID " + id);
        }

        producto.setIdProducto(id);

        validarProducto(producto);

        return productoRepository.save(producto);
    }

    @Override
    public void eliminar(Integer id) {

        if (!productoRepository.existsById(id)) {
            throw new RuntimeException("Producto no se encontró con ID " + id);
        }

        productoRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Integer id) {
        return productoRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Producto> buscarPorEstado(Integer estado) {
        return productoRepository.findByEstado(estado);
    }


    // VALIDACIONES

    private void validarProducto(Producto producto) {

        if (producto.getNombreProducto() == null || producto.getNombreProducto().trim().isEmpty()) {
            throw new IllegalArgumentException("El nombre del producto es obligatorio");
        }

        if (producto.getPrecio() <= 0) {
            throw new IllegalArgumentException("El precio debe ser mayor a 0");
        }

        if (producto.getStock() < 0) {
            throw new IllegalArgumentException("El stock no puede ser negativo");
        }

    }

}