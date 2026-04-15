package com.kennygudiel.Kinalapp.repository;

import com.kennygudiel.Kinalapp.entity.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Integer>
{
    List<Producto> findByEstado(Integer estado);
}
