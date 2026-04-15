package com.kennygudiel.Kinalapp.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "ventas")
public class Venta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "codigo_venta")
    private Integer idVenta;

    @Column(name = "fecha_venta")
    private String fechaVenta;

    @Column
    private Double total;

    @Column
    private int estado;


    // 🔹 MANY TO ONE -> CLIENTE
    @ManyToOne
    @JoinColumn(name = "clientes_dpi_cliente")
    private Cliente cliente;


    // 🔹 MANY TO ONE -> USUARIO
    @ManyToOne
    @JoinColumn(name = "usuarios_codigo_usuario")
    private Usuario usuario;


    public Venta() {
    }


    public Venta(Integer idVenta, String fechaVenta, Double total, int estado,
                 Cliente cliente, Usuario usuario) {
        this.idVenta = idVenta;
        this.fechaVenta = fechaVenta;
        this.total = total;
        this.estado = estado;
        this.cliente = cliente;
        this.usuario = usuario;
    }


    public Integer getIdVenta() {
        return idVenta;
    }

    public void setIdVenta(Integer idVenta) {
        this.idVenta = idVenta;
    }

    public String getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(String fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getEstado() {
        return estado;
    }

    public void setEstado(int estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }
}