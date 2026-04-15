package com.kennygudiel.Kinalapp.repository;

import com.kennygudiel.Kinalapp.entity.Venta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface VentaRepository extends JpaRepository<Venta, Integer> {

    List<Venta> findByEstado(Integer estado);

}
