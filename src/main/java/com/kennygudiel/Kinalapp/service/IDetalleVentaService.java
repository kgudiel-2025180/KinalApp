package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.DetalleVenta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IDetalleVentaService {

    //Interfaz: Es un contrato que dice que métodos debe tener
    //cualquier servicio de DetalleVenta
    //no tiene implementación, solo la definición de los métodos


    @Transactional(readOnly = true)
    List<DetalleVenta> listarDetalles();


    //Metodo que guarda un DetalleVenta en la BD
    DetalleVenta guardar(DetalleVenta detalleVenta);
    //Parametros - Recibe un objeto DetalleVenta con los datos a guardar


    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<DetalleVenta> buscarPorId(Integer id);


    //Metodo que actualiza un DetalleVenta
    DetalleVenta actualizar(Integer id, DetalleVenta detalleVenta);
    //Parametros - id: ID del detalle a actualizar
    //DetalleVenta detalleVenta: Objeto con los datos nuevos
    //Retorna un objeto actualizado


    //Metodo para eliminar
    void eliminar(Integer id);


    //boolean - Retorna true si existe y false si no existe
    boolean existePorId(Integer id);


    List<DetalleVenta> buscarPorEstado(Integer estado);

}