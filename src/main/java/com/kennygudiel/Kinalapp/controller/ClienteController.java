package com.kennygudiel.Kinalapp.controller;


import com.kennygudiel.Kinalapp.entity.Cliente;
import com.kennygudiel.Kinalapp.repository.ClienteRepository;
import com.kennygudiel.Kinalapp.service.ClienteService;
import com.kennygudiel.Kinalapp.service.IClienteService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RestController = @Controller + @ResponseBody
@RequestMapping("/clientes")
//Todas las rutas en este controlador deben empezar con /clientes
public class ClienteController {

    public final IClienteService clienteService;

    //Como buena practica la Inyeccion de dependencia debe hacerse por el constructor
    public ClienteController(IClienteService clienteService){ this.clienteService = clienteService; }

    //Responde a peticiones GET
    @GetMapping
    //ResponseEntity nos permite controlar el codifo HTTP y el cuerpo
    public ResponseEntity <List<Cliente>> listarClientes(){
      List<Cliente> clientes = clienteService.listarClientes();
      //delegamos al servicio
      return ResponseEntity.ok(clientes);
      //200 OK con la lista de clientes
    }

    //{dpi} es una variable de ruta(valor a buscar)
    @GetMapping("/{dpi}")
    public ResponseEntity<Cliente> buscaPorDPI(@PathVariable String dpi){
        //@PathVariable toma el valor y lo asigna al dpi
        return clienteService.buscarPorDPI(dpi)
                //Si Optional tiene valor, devuelve 200 ok con el cliente
                .map(ResponseEntity::ok)
                //Si Optional esta vacio, devulve 404 NOT FOUND
                .orElse(ResponseEntity.notFound().build());
    }

    //POST crar un nuevo cliente
    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody Cliente cliente) {
        //@RequestBody: Toma el JSON del cuerpo y lo convierte a un objeto de tipo Cliente
        //<?> significa "tipo generico" puede se un Cliente o un String
        try {
            Cliente nuevoCliente = clienteService.guardar(cliente);
            //Intentamos guardar el cliente pero puede lanzar una excepcion
            //de IllegalArgumentException
            return new ResponseEntity<>(nuevoCliente, HttpStatus.CREATED);
            //201 CREATED(mucho mas especifico que el 200 para la creacion de un cliente)
        } catch (IllegalArgumentException e) {
            //Si hay error de validacion
            return ResponseEntity.badRequest().body(e.getMessage());
            //400 BAD REQUEST con el mensaje de error
        }

    }
    //DELETE elimina un cliente
    @DeleteMapping("/{dpi}")
    public ResponseEntity<Void> eliminar(@PathVariable String dpi){
        //ResponseEntity<Void>: No devuelve cuerpo en la respuesta
        try{
            if (!clienteService.existePorDPI(dpi)){
                return ResponseEntity.notFound().build();
                //404 si no existe
            }
            clienteService.eliminar(dpi);
            return ResponseEntity.noContent().build();
            //204 NO CONTENT (se ejecuto correctamente y no devuelve cuerpo)
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
            //404 NOT FOUND
        }
    }


    //Actualizar cliente a traves de DPI
    @PutMapping("/{dpi}")
    public ResponseEntity<?> actualizar(@PathVariable String dpi, @RequestBody Cliente cliente){
        try{
            if(!clienteService.existePorDPI(dpi)){
                //Verificar si existe antes de poder actualizar
                //404 NOT FOUND
                return ResponseEntity.notFound().build();
            }
            //Actualizar el cliente pero esto puede lanzar un excepcion
            Cliente clienteActualizado = clienteService.actualizar(dpi,cliente);
            return ResponseEntity.ok(clienteActualizado);
            //200 ok con el cliente ya actualizado
        }catch(IllegalArgumentException e){
            //Error cuando los datos son incorrectos
            return ResponseEntity.badRequest().body(e.getMessage());
        }catch (RuntimeException e){
            //Posiblemente cualquier otro error como: Cliente no encontrado, etc.
            //404 NOT FOUND
            return  ResponseEntity.notFound().build();

        }
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Cliente>> buscarPorEstado(@PathVariable Integer estado) {

        List<Cliente> clientes = clienteService.buscarPorEstado(estado);

        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(clientes);
    }

}




















