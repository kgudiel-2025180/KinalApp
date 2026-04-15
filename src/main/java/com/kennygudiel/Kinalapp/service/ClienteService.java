package com.kennygudiel.Kinalapp.service;

import com.kennygudiel.Kinalapp.entity.Cliente;
import com.kennygudiel.Kinalapp.repository.ClienteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

//Anotación que registra un Bean como un Ban de Spring
//Que la clase contiene la lógica del negocio
@Service
//Por defecto todos los métodos de esta clase serán
//transacciones
//Una transacción es que puede o no ocurrir algo
@Transactional

public class ClienteService implements IClienteService {
    /* private: solo accesible dentro de la clase
       ClienteRepository: Es el repositorio para acceder a la BD
       Inyección de Dependencias Spring nos da el repositorio

     */

    private final ClienteRepository clienteRepository;

    public ClienteService(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    /*
     * Contructor: Este se ejecuta al crear el repositorio
     * Parametros: Spring pasa el repositorio automaticamnete y a esto se le conoce
     * como Inyeccion de Dependencias
     * Asignamos el repositorio a nuestra variable de clase
     */


    /*
     * @Override: Indica que etsamos implementando un metodo de la interfaz
     */




    @Override
    public List<Cliente> listarClientes() {
        return clienteRepository.findAll();
    }


    @Override
    public Cliente guardar(Cliente cliente) {
        validarCliente(cliente);
        return clienteRepository.save(cliente);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Cliente> buscarPorDPI(String dpi) {
        //Buscar un cliente por DPI
        return clienteRepository.findById(dpi);
        //Optional nos evita el NullPointerException
    }

    @Override
    public Cliente actualizar(String dpi, Cliente cliente) {
        //Actualiza un cliente existente
        if (!clienteRepository.existsById(dpi)) {
            throw new RuntimeException("Cliente mo se encontro con DPI" + dpi);
            //Si no existe, se lanza una excepción
        }
        /*
         * 1. Asegura que el DPI del objeto coincida con el de la URL
         * 2. por seguridad usamos el DPI dem la URL y no el que viene en el JSON
         */
        cliente.setDpiCliente(dpi);
        validarCliente(cliente);

        return clienteRepository.save(cliente);
    }

    @Override
    public void eliminar(String dpi) {
        //Eliminar un cliente
        if (!clienteRepository.existsById(dpi)) {
            throw new RuntimeException("El Cliente no se encontro con el DPI" + dpi);
        }
        clienteRepository.deleteById(dpi);

    }

    @Override
    @Transactional(readOnly = true)
    public boolean existePorDPI(String dpi) {
        //Verificar si exite el cliente
        return clienteRepository.existsById(dpi);
        //retorna true o false
    }

    //Metodos privados(solo puede utilizarse dentro de la clase)
    private void validarCliente(Cliente cliente) {
        /*
         * Validaciones del negocio: Este metodo se hará privado porque
         * es algo interno del servicio
         */
        if (cliente.getDpiCliente() == null || cliente.getDpiCliente().trim().isEmpty()) {
            //si el DPI es null o esta vacio después de quitar espacios
            //lanza una exprecion con un mensaje
            throw new IllegalArgumentException("El DPI es un dato obligatorio");
        }

        if (cliente.getNombreCliente() == null || cliente.getNombreCliente().trim().isEmpty()) {
            throw new IllegalArgumentException(("El nombre es un dato obligatorio"));
        }
        if (cliente.getApellidoCliente() == null || cliente.getApellidoCliente().trim().isEmpty()) {
            throw new IllegalArgumentException("El apellido es un dato obligatorio");
        }
    }
    @Override
    @Transactional(readOnly = true)
    public List<Cliente> buscarPorEstado(Integer estado) {
        return clienteRepository.findByEstado(estado);
    }

}
