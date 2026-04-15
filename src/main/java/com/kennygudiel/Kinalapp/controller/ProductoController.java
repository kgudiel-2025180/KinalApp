package com.kennygudiel.Kinalapp.controller;

import com.kennygudiel.Kinalapp.entity.Producto;
import com.kennygudiel.Kinalapp.service.IProductoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/productos")
//Todas las rutas empiezan con /productos
public class ProductoController {

    public final IProductoService productoService;

    //Inyeccion por constructor
    public ProductoController(IProductoService productoService){
        this.productoService = productoService;
    }


    //GET listar productos
    @GetMapping
    public ResponseEntity<List<Producto>> listarProductos(){

        List<Producto> productos = productoService.listarProductos();

        return ResponseEntity.ok(productos);
    }


    //GET buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Producto> buscarPorId(@PathVariable Integer id){

        return productoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }


    //POST crear producto
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Producto producto){

        try{

            Producto nuevoProducto = productoService.guardar(producto);

            return new ResponseEntity<>(nuevoProducto, HttpStatus.CREATED);

        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


    //DELETE eliminar producto
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id){

        try{

            if(!productoService.existePorId(id)){
                return ResponseEntity.notFound().build();
            }

            productoService.eliminar(id);

            return ResponseEntity.noContent().build();

        }catch (RuntimeException e){

            return ResponseEntity.notFound().build();

        }

    }


    //PUT actualizar producto
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(@PathVariable Integer id,
                                        @RequestBody Producto producto){

        try{

            if(!productoService.existePorId(id)){
                return ResponseEntity.notFound().build();
            }

            Producto productoActualizado =
                    productoService.actualizar(id, producto);

            return ResponseEntity.ok(productoActualizado);

        }catch (IllegalArgumentException e){

            return ResponseEntity.badRequest().body(e.getMessage());

        }catch (RuntimeException e){

            return ResponseEntity.notFound().build();

        }

    }


    //GET buscar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Producto>> buscarPorEstado(@PathVariable Integer estado){

        List<Producto> productos = productoService.buscarPorEstado(estado);

        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(productos);

    }

}
