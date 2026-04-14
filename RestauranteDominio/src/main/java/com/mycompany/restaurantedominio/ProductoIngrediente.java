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
 * Entidad que representa la relación entre un {@link Producto}
 * y un {@link Ingrediente}.
 * 
 * Esta clase modela una relación muchos a muchos con atributos adicionales,
 * en este caso, la cantidad de ingrediente requerida para un producto.
 * 
 * Está mapeada a la tabla "producto_ingredientes" en la base de datos.
 * 
 * Función en el dominio:
 * - Define qué ingredientes componen un producto
 * - Indica la cantidad necesaria de cada ingrediente
 * - Permite validar disponibilidad de productos en función del stock
 * 
 */
@Entity
@Table(name = "producto_ingredientes")
public class ProductoIngrediente implements Serializable {

    @Id
    @Column(name = "id_producto_ingrediente")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto producto;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingrediente;

    @Column(name = "cantidad", nullable = false)
    private Integer cantidad;

    
    /**
     * Constructor vacío requerido por JPA.
     */
    public ProductoIngrediente() {
    }
    
    /**
     * Constructor para crear la relación producto-ingrediente.
     * 
     * @param producto producto asociado
     * @param ingrediente ingrediente asociado
     * @param cantidad cantidad requerida del ingrediente
     */
    public ProductoIngrediente(Producto producto, Ingrediente ingrediente, Integer cantidad) {
        this.producto = producto;
        this.ingrediente = ingrediente;
        this.cantidad = cantidad;
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Producto getProducto() {
        return producto;
    }

    public void setProducto(Producto producto) {
        this.producto = producto;
    }

    public Ingrediente getIngrediente() {
        return ingrediente;
    }

    public void setIngrediente(Ingrediente ingrediente) {
        this.ingrediente = ingrediente;
    }

    public Integer getCantidad() {
        return cantidad;
    }

    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ProductoIngrediente)) {
            return false;
        }
        ProductoIngrediente other = (ProductoIngrediente) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "ProductoIngrediente[ id=" + id + " ]";
    }
}
