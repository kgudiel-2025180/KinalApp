package com.kennygudiel.Kinalapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "productos")
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // ✅ AGREGAR ESTO
    @Column(name = "id_producto")
    private Integer idProducto;  // ✅ Cambiar a Integer (puede ser null para nuevo)

    @Column(name = "nombre_producto")
    private String nombreProducto;

    @Column
    private String descripcion;

    @Column
    private Double precio;  // ✅ Cambiar a Double

    @Column
    private Integer stock;  // ✅ Cambiar a Integer

    @Column
    private Integer estado;  // ✅ Cambiar a Integer

    public Producto() {
    }

    public Producto(Integer idProducto, String nombreProducto, String descripcion,
                    Double precio, Integer stock, Integer estado) {
        this.idProducto = idProducto;
        this.nombreProducto = nombreProducto;
        this.descripcion = descripcion;
        this.precio = precio;
        this.stock = stock;
        this.estado = estado;
    }

    // Getters y Setters
    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getEstado() {
        return estado;
    }

    public void setEstado(Integer estado) {
        this.estado = estado;
    }
}