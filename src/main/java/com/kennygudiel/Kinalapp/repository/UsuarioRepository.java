package com.kennygudiel.Kinalapp.repository;

import com.kennygudiel.Kinalapp.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    List<Usuario> findByEstado(Integer estado);

    Optional<Usuario> findByUsername(String username);

    Optional<Usuario> findByUsernameAndPassword(String username, String password);



}
