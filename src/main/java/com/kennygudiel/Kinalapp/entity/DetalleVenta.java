package com.kennygudiel.Kinalapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "detalle_venta")
public class DetalleVenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_detalle_venta")
    private Integer idDetalle;

    @Column
    private Integer cantidad;

    @Column(name = "precio_unitario")
    private Double precio;

    @Column
    private Double subtotal;

    @Column
    private int estado;


    // MANY TO ONE -> VENTA
    @ManyToOne
    @JoinColumn(name = "ventas_codigo_venta")
    private Venta venta;


    // MANY TO ONE -> PRODUCTO
    @ManyToOne
    @JoinColumn(name = "productos_codigo_producto")
    private Producto producto;


    public DetalleVenta() {
    }


    public DetalleVenta(Integer idDetalle,
                        Integer cantidad,
                        Double precio,
                        Double subtotal,
                        int estado,
                        Venta venta,
                        Producto producto) {

        this.idDetalle = idDetalle;
        this.cantidad = cantidad;
        this.precio = precio;
        this.subtotal = subtotal;
        this.estado = estado;
        this.venta = venta;
        this.producto = producto;
    }


    public Integer getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(Integer idDetalle) {
        this.idDetalle = idDetalle;
    }


    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }


    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }


    public Double getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Double subtotal) {
        this.subtotal = subtotal;
    }


    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }


    public Venta getVenta() {
        return venta;
    }

    public void setVenta(Venta venta) {
        this.venta = venta;
    }


    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }
}