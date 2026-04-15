package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Venta;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IVentaService {

    //Interfaz: Es un contrato que dice que métodos debe tener
    //cualquier servicio de Ventas
    //no tiene implementación, solo la definición de los métodos


    @Transactional(readOnly = true)
    List<Venta> listarVentas();


    //Metodo que guarda una Venta en la BD
    Venta guardar(Venta venta);
    //Parametros - Recibe un objeto Venta con los datos a guardar


    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Venta> buscarPorId(Integer id);


    //Metodo que actualiza una Venta
    Venta actualizar(Integer id, Venta venta);
    //Parametros - id: ID de la venta a actualizar
    //Venta venta: Objeto con los datos nuevos
    //Retorna un objeto de tipo Venta ya actualizado


    //Metodo de tipo void para eliminar una Venta
    //void: no retorna ningun dato
    //Elimina una venta por su ID
    void eliminar(Integer id);


    //boolean - Retorna true si existe y false si no existe
    boolean existePorId(Integer id);


    List<Venta> buscarPorEstado(Integer estado);

}
