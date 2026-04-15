package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.DetalleVenta;
import com.kennygudiel.Kinalapp.repository.DetalleVentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class DetalleVentaService implements IDetalleVentaService {

    private final DetalleVentaRepository detalleVentaRepository;

    public DetalleVentaService(DetalleVentaRepository detalleVentaRepository) {
        this.detalleVentaRepository = detalleVentaRepository;
    }


    @Override
    public List<DetalleVenta> listarDetalles() {
        return detalleVentaRepository.findAll();
    }


    @Override
    public DetalleVenta guardar(DetalleVenta detalleVenta) {

        validarDetalle(detalleVenta);

        return detalleVentaRepository.save(detalleVenta);
    }


    @Override
    @Transactional(readOnly = true)
    public Optional<DetalleVenta> buscarPorId(Integer id) {

        return detalleVentaRepository.findById(id);

    }


    @Override
    public DetalleVenta actualizar(Integer id, DetalleVenta detalleVenta) {

        if (!detalleVentaRepository.existsById(id)) {

            throw new RuntimeException(
                    "Detalle no se encontró con ID " + id);

        }

        detalleVenta.setIdDetalle(id);

        validarDetalle(detalleVenta);

        return detalleVentaRepository.save(detalleVenta);

    }


    @Override
    public void eliminar(Integer id) {

        if (!detalleVentaRepository.existsById(id)) {

            throw new RuntimeException(
                    "Detalle no se encontró con ID " + id);

        }

        detalleVentaRepository.deleteById(id);

    }


    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Integer id) {

        return detalleVentaRepository.existsById(id);

    }


    @Override
    @Transactional(readOnly = true)
    public List<DetalleVenta> buscarPorEstado(Integer estado) {

        return detalleVentaRepository.findByEstado(estado);

    }



    // ✅ VALIDACIONES CORREGIDAS

    private void validarDetalle(DetalleVenta detalleVenta) {

        if (detalleVenta.getVenta() == null) {

            throw new IllegalArgumentException(
                    "La venta es obligatoria");
        }

        if (detalleVenta.getProducto() == null) {

            throw new IllegalArgumentException(
                    "El producto es obligatorio");
        }

        if (detalleVenta.getCantidad() == null ||
                detalleVenta.getCantidad() <= 0) {

            throw new IllegalArgumentException(
                    "La cantidad debe ser mayor a 0");
        }

        if (detalleVenta.getPrecio() == null ||
                detalleVenta.getPrecio() < 0) {

            throw new IllegalArgumentException(
                    "El precio no puede ser negativo");
        }

    }

}