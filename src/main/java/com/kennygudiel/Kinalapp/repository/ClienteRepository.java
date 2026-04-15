package com.kennygudiel.Kinalapp.repository;

import com.kennygudiel.Kinalapp.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente,String>
{
    List<Cliente> findByEstado(Integer estado);
}

