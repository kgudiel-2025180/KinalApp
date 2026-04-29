package com.kennygudiel.Kinalapp.controller;

import com.kennygudiel.Kinalapp.entity.Venta;
import com.kennygudiel.Kinalapp.service.IVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping("/ventas")
//Todas las rutas empiezan con /ventas
public class VentaController {

    public final IVentaService ventaService;


    //Inyeccion por constructor
    public VentaController(IVentaService ventaService) {
        this.ventaService = ventaService;
    }


    //GET listar ventas
    @GetMapping
    public ResponseEntity<List<Venta>> listarVentas() {

        List<Venta> ventas = ventaService.listarVentas();

        return ResponseEntity.ok(ventas);
    }


    //GET buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscarPorId(@PathVariable Integer id) {

        return ventaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }


    //POST crear venta
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Venta venta) {

        try {

            Venta nuevaVenta = ventaService.guardar(venta);

            return new ResponseEntity<>(nuevaVenta, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


    //DELETE eliminar venta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        try {

            if (!ventaService.existePorId(id)) {
                return ResponseEntity.notFound().build();
            }

            ventaService.eliminar(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }


    //PUT actualizar venta
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody Venta venta) {

        try {

            if (!ventaService.existePorId(id)) {
                return ResponseEntity.notFound().build();
            }

            Venta ventaActualizada =
                    ventaService.actualizar(id, venta);

            return ResponseEntity.ok(ventaActualizada);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }

    }


    //GET buscar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Venta>> buscarPorEstado(
            @PathVariable Integer estado) {

        List<Venta> ventas =
                ventaService.buscarPorEstado(estado);

        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(ventas);
    }



}
