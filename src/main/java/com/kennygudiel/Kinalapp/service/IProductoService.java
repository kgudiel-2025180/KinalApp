package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Producto;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

public interface IProductoService {

    //Interfaz: Es un contrato que dice que métodos debe tener
    //cualquier servicio de Productos
    //no tiene implementación, solo la definición de los métodos


    @Transactional(readOnly = true)
    List<Producto> listarProductos();


    //Metodo que guarda un Producto en la BD
    Producto guardar(Producto producto);
    //Parametros - Recibe un objeto Producto con los datos a guardar


    //Optional - Contenedor que puede o no tener un valor
    //evita el error de NullPointerException
    Optional<Producto> buscarPorId(Integer id);


    //Metodo que actualiza un Producto
    Producto actualizar(Integer id, Producto producto);
    //Parametros - id: ID del producto a actualizar
    //Producto producto: Objeto con los datos nuevos
    //Retorna un objeto de tipo Producto ya actualizado


    //Metodo de tipo void para eliminar un Producto
    //void: no retorna ningun dato
    //Elimina un producto por su ID
    void eliminar(Integer id);


    //boolean - Retorna true si existe y false si no existe
    boolean existePorId(Integer id);


    List<Producto> buscarPorEstado(Integer estado);

}
