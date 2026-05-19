package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Venta;
import com.kennygudiel.Kinalapp.repository.VentaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class VentaService implements IVentaService {

    private final VentaRepository ventaRepository;

    public VentaService(VentaRepository ventaRepository) {
        this.ventaRepository = ventaRepository;
    }

    @Override
    public List<Venta> listarVentas() {
        return ventaRepository.findAll();
    }

    @Override
    public Venta guardar(Venta venta) {
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Venta> buscarPorId(Integer id) {
        return ventaRepository.findById(id);
    }

    @Override
    public Venta actualizar(Integer id, Venta venta) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no se encontró con ID " + id);
        }
        venta.setIdVenta(id);
        validarVenta(venta);
        return ventaRepository.save(venta);
    }

    @Override
    public void eliminar(Integer id) {
        if (!ventaRepository.existsById(id)) {
            throw new RuntimeException("Venta no se encontró con ID " + id);
        }
        ventaRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorId(Integer id) {
        return ventaRepository.existsById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Venta> buscarPorEstado(Integer estado) {
        return ventaRepository.findByEstado(estado);
    }

    // VALIDACIONES CORREGIDAS
    private void validarVenta(Venta venta) {

        if (venta.getFechaVenta() == null ||
                venta.getFechaVenta().trim().isEmpty()) {
            throw new IllegalArgumentException("La fecha de la venta es obligatoria");
        }

        if (venta.getTotal() == null || venta.getTotal() < 0) {
            throw new IllegalArgumentException("El total no puede ser negativo");
        }

        // Validar que el cliente existe
        if (venta.getCliente() == null) {
            throw new IllegalArgumentException("El cliente es obligatorio");
        }
    }
}