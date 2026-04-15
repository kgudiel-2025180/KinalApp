package com.kennygudiel.Kinalapp.controller;

import com.kennygudiel.Kinalapp.entity.DetalleVenta;
import com.kennygudiel.Kinalapp.service.IDetalleVentaService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/detalleventa")
//Todas las rutas empiezan con /detalleventa
public class DetalleVentaController {

    public final IDetalleVentaService detalleVentaService;


    //Inyeccion por constructor
    public DetalleVentaController(IDetalleVentaService detalleVentaService) {
        this.detalleVentaService = detalleVentaService;
    }


    //GET listar detalles
    @GetMapping
    public ResponseEntity<List<DetalleVenta>> listarDetalles() {

        List<DetalleVenta> detalles =
                detalleVentaService.listarDetalles();

        return ResponseEntity.ok(detalles);
    }


    //GET buscar por ID
    @GetMapping("/{id}")
    public ResponseEntity<DetalleVenta> buscarPorId(
            @PathVariable Integer id) {

        return detalleVentaService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());

    }


    //POST crear detalle
    @PostMapping
    public ResponseEntity<?> guardar(
            @RequestBody DetalleVenta detalleVenta) {

        try {

            DetalleVenta nuevo =
                    detalleVentaService.guardar(detalleVenta);

            return new ResponseEntity<>(
                    nuevo,
                    HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        }

    }


    //DELETE eliminar detalle
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(
            @PathVariable Integer id) {

        try {

            if (!detalleVentaService.existePorId(id)) {

                return ResponseEntity
                        .notFound()
                        .build();
            }

            detalleVentaService.eliminar(id);

            return ResponseEntity
                    .noContent()
                    .build();

        } catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();

        }

    }


    //PUT actualizar detalle
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody DetalleVenta detalleVenta) {

        try {

            if (!detalleVentaService.existePorId(id)) {

                return ResponseEntity
                        .notFound()
                        .build();
            }

            DetalleVenta actualizado =
                    detalleVentaService.actualizar(
                            id,
                            detalleVenta);

            return ResponseEntity.ok(actualizado);

        } catch (IllegalArgumentException e) {

            return ResponseEntity
                    .badRequest()
                    .body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity
                    .notFound()
                    .build();

        }

    }


    //GET buscar por estado
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<DetalleVenta>> buscarPorEstado(
            @PathVariable Integer estado) {

        List<DetalleVenta> detalles =
                detalleVentaService.buscarPorEstado(estado);

        if (detalles.isEmpty()) {

            return ResponseEntity
                    .noContent()
                    .build();
        }

        return ResponseEntity.ok(detalles);
    }

}
