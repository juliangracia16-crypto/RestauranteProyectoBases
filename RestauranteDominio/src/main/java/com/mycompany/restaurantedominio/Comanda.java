/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedominio;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author PC GAMER MASTER RACE
 */
@Entity
@Table(name= "comandas")
public class Comanda implements Serializable {

    @Id
    @Column(name = "id_comanda")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "folio", unique = true, nullable = false)
    private String folio;
    
    @Column(name = "fecha_hora_creacion", nullable = false)
    private LocalDateTime fechaHora;
    
    @Column(name = "total")
    private double total;
    
    @Column(name = "estado_comanda" , nullable = false)
    private EstadoComanda estado;
    
    @ManyToOne()
    @JoinColumn(name = "id_mesa", nullable = false)
    private Mesa mesa;
    
    @ManyToOne()
    @JoinColumn(name = "id_cliente", nullable = false)
    private Cliente cliente;
    
    @OneToMany(mappedBy = "comanda", cascade = CascadeType.PERSIST)
    private List<ProductoSeleccionado> productosSeleccionados;

    public Comanda() {
    }

    public Comanda(String folio, LocalDateTime fechaHora, double total, EstadoComanda estado, Mesa mesa, Cliente cliente, List<ProductoSeleccionado> productosSeleccionados) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.mesa = mesa;
        this.cliente = cliente;
        this.productosSeleccionados = productosSeleccionados;
    }
    
    public Comanda(String folio, LocalDateTime fechaHora, double total, EstadoComanda estado) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
    }

    public Comanda(String folio, LocalDateTime fechaHora, double total, EstadoComanda estado, Cliente cliente) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.cliente = cliente;
    }

    public Comanda(String folio, LocalDateTime fechaHora, double total, EstadoComanda estado, Mesa mesa) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.mesa = mesa;
    }

    public Comanda(String folio, LocalDateTime fechaHora, double total, EstadoComanda estado, Mesa mesa, Cliente cliente) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
        this.mesa = mesa;
        this.cliente = cliente;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFolio() {
        return folio;
    }

    public void setFolio(String folio) {
        this.folio = folio;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public void setFechaHora(LocalDateTime fechaHora) {
        this.fechaHora = fechaHora;
    }

    public double getTotal() {
        return total;
    }

    public void setTotal(double total) {
        this.total = total;
    }

    public EstadoComanda getEstado() {
        return estado;
    }

    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }

    public Mesa getMesa() {
        return mesa;
    }

    public List<ProductoSeleccionado> getProductosSeleccionados() {
        return productosSeleccionados;
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Comanda)) {
            return false;
        }
        Comanda other = (Comanda) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Comanda[ id=" + id + " ]";
    }
    
}
