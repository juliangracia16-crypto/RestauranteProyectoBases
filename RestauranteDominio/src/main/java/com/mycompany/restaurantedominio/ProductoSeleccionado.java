/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Entidad que representa un producto seleccionado dentro de una comanda.
 * 
 * Esta clase modela la relación entre una comanda y los productos que contiene,
 * incluyendo la cantidad, precio unitario, subtotal y posibles comentarios.
 * 
 * Se utiliza para persistir los productos que un cliente ha ordenado.
 * 
 */
@Entity
@Table(name = "productos_seleccionados")
public class ProductoSeleccionado implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_producto_seleccionado")
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "id_comanda", nullable = false)
    private Comanda comanda;
    
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;
    
    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;
    
    @Column(name = "precio_unitario", nullable = false)
    private Double precioUnitario;
    
    @Column(name = "subtotal", nullable = false)
    private Double subtotal;
    
    @Column(name = "comentario")
    private String comentario;
    
    /**
     * Constructor vacío requerido por JPA.
     */
    public ProductoSeleccionado() {}
    
    /**
     * Constructor principal para crear un producto seleccionado.
     * Calcula automáticamente el subtotal.
     * 
     * @param comanda Comanda asociada
     * @param producto Producto seleccionado
     * @param cantidad Cantidad del producto
     * @param precioUnitario Precio unitario del producto
     * @param comentario Comentario opcional
     */
    public ProductoSeleccionado(Comanda comanda, Producto producto, Integer cantidad, Double precioUnitario, String comentario) {
        this.comanda = comanda;
        this.producto = producto;
        this.cantidad = cantidad;
        this.precioUnitario = precioUnitario;
        this.subtotal = precioUnitario * cantidad;
        this.comentario = comentario;
    }
    
    // GETTERS Y SETTERS
    public Long getId() { 
        return id; 
    }
    
    public void setId(Long id) { 
        this.id = id; 
    }
    
    public Comanda getComanda() { 
        return comanda; 
    }
    
    public void setComanda(Comanda comanda) { 
        this.comanda = comanda; 
    }
    
    public Producto getProducto() { 
        return producto; 
    }
    
    public void setProducto(Producto producto) { 
        this.producto = producto; 
    }
    
    public Integer getCantidad() { 
        return cantidad; 
    }
    
    public void setCantidad(Integer cantidad) { 
        this.cantidad = cantidad; 
    }
    
    public Double getPrecioUnitario() { 
        return precioUnitario; 
    }
    
    public void setPrecioUnitario(Double precioUnitario) { 
        this.precioUnitario = precioUnitario; 
    }
    
    public Double getSubtotal() { 
        return subtotal; 
    }
    
    public void setSubtotal(Double subtotal) { 
        this.subtotal = subtotal; 
    }
    
    public String getComentario() { 
        return comentario; 
    }
    
    public void setComentario(String comentario) { 
        this.comentario = comentario; 
    }
    
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }
    
    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProductoSeleccionado)) return false;
        ProductoSeleccionado other = (ProductoSeleccionado) object;
        if ((this.id == null && other.id != null) ||
            (this.id != null && !this.id.equals(other.id))) return false;
        return true;
    }
    
    @Override
    public String toString() {
        return "ProductoSeleccionado[ id=" + id + " ]";
    }
    
}
