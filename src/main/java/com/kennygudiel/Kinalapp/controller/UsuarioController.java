package com.kennygudiel.Kinalapp.controller;

import com.kennygudiel.Kinalapp.entity.Usuario;
import com.kennygudiel.Kinalapp.service.IUsuarioService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private final IUsuarioService usuarioService;

    public UsuarioController(IUsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // LISTAR
    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {

        List<Usuario> usuarios = usuarioService.listarUsuarios();

        return ResponseEntity.ok(usuarios);
    }


    // BUSCAR POR ID
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Integer id) {

        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }


    // GUARDAR
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Usuario usuario) {

        try {

            Usuario nuevo = usuarioService.guardar(usuario);

            return new ResponseEntity<>(nuevo, HttpStatus.CREATED);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        }

    }


    // ELIMINAR
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {

        try {

            if (!usuarioService.existePorId(id)) {
                return ResponseEntity.notFound().build();
            }

            usuarioService.eliminar(id);

            return ResponseEntity.noContent().build();

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }
    }


    // ACTUALIZAR
    @PutMapping("/{id}")
    public ResponseEntity<?> actualizar(
            @PathVariable Integer id,
            @RequestBody Usuario usuario) {

        try {

            if (!usuarioService.existePorId(id)) {
                return ResponseEntity.notFound().build();
            }

            Usuario actualizado = usuarioService.actualizar(id, usuario);

            return ResponseEntity.ok(actualizado);

        } catch (IllegalArgumentException e) {

            return ResponseEntity.badRequest().body(e.getMessage());

        } catch (RuntimeException e) {

            return ResponseEntity.notFound().build();

        }
    }


    // BUSCAR POR ESTADO
    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Usuario>> buscarPorEstado(
            @PathVariable Integer estado) {

        List<Usuario> usuarios =
                usuarioService.buscarPorEstado(estado);

        if (usuarios.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(usuarios);
    }

     // BUSCAR POR USERNAME
    @GetMapping("/username/{username}")
    public ResponseEntity<Usuario> buscarPorUsername(
            @PathVariable String username) {

        return usuarioService.buscarPorUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

}