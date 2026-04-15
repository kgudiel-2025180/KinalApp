package com.kennygudiel.Kinalapp.repository;

import com.kennygudiel.Kinalapp.entity.DetalleVenta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DetalleVentaRepository extends JpaRepository<DetalleVenta, Integer> {

    List<DetalleVenta> findByEstado(Integer estado);

}
