package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Usuario;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IUsuarioService {

    //Interfaz: contrato que define los métodos del servicio Usuario


    @Transactional(readOnly = true)
    List<Usuario> listarUsuarios();


    //Guardar usuario
    Usuario guardar(Usuario usuario);


    //Buscar por ID
    Optional<Usuario> buscarPorId(Integer id);


    //Actualizar usuario
    Usuario actualizar(Integer id, Usuario usuario);


    //Eliminar usuario
    void eliminar(Integer id);


    //Verificar si existe
    boolean existePorId(Integer id);


    //Buscar por estado
    List<Usuario> buscarPorEstado(Integer estado);


    //Buscar por username (para login)
    Optional<Usuario> buscarPorUsername(String username);

}
