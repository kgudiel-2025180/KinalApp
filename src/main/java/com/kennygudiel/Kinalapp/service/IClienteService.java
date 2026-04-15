package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Cliente;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IClienteService {
    //Interfaz: Es un contrato que dice que métodos debe tener
    //cualquier servicio de Clientes no tiene
    //implementación, solo la definición de los métodos


    @Transactional(readOnly = true)
    List<Cliente> listarClientes();


    //Metodo que guarda un Cliente en la BD
    Cliente guardar(Cliente cliente);
    //Parametros - Recibe un objeto Cliente con los datos a guardar

    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Cliente> buscarPorDPI(String dpi);

    //Metodo qye actualiza un Cliente
    Cliente actualizar(String dpi, Cliente cliente);
    //Parametros - dpi: DPI del Cliente a actualizar
    //Cliente cliente: Objeto con los datos nuevos
    //Retorna un objeto de tipo Cliente ya actualizado

    //Metodo de tipo void para eliminar a un Cliente
    //void: no retorna ningun dato
    //Elimina un Cliente por su DPI
    void eliminar(String dpi);

    //boolean - Retorna tru si existe y false si no existe
    boolean existePorDPI(String dpi);

    List<Cliente> buscarPorEstado(Integer estado);



}
