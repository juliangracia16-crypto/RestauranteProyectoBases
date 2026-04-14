package com.mycompany.restaurantedominio;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * Entidad que representa un producto dentro del sistema del restaurante.
 * 
 * Esta clase es parte del dominio y está mapeada a la tabla "productos"
 * en la base de datos mediante JPA.
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable {

    @Id
    @Column(name = "id_producto")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "descripcion", length = 255)
    private String descripcion;

    @Column(name = "precio", nullable = false)
    private Double precio;

    @Enumerated(EnumType.STRING)
    @Column(name = "tipo", nullable = false)
    private TipoProducto tipo;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado", nullable = false)
    private EstadoProducto estado;

    @Lob
    @Column(name = "imagen")
    private byte[] imagen;

    @OneToMany(mappedBy = "producto", fetch = FetchType.EAGER)
    private List<ProductoIngrediente> ingredientes;

    /**
     * Constructor vacío requerido por JPA
     */
    public Producto() {
    }
    /**
     * Constructor para crear un producto.
     * 
     * El estado se establece automáticamente como ACTIVO.
     * 
     * @param nombre nombre del producto
     * @param descripcion descripción del producto
     * @param precio precio del producto
     * @param tipo tipo de producto
     */
    public Producto(String nombre, String descripcion, Double precio, TipoProducto tipo) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.estado = EstadoProducto.ACTIVO; // Por defecto al instanciar
    }

    /**
     * Determina si el producto está disponible para ser vendido.
     * 
     * Reglas:
     * - Debe tener ingredientes
     * - Debe estar en estado ACTIVO
     * - Todos los ingredientes deben tener stock suficiente
     * 
     * @return true si el producto está disponible, false en caso contrario
     */
    public boolean isDisponible() {
        if (ingredientes == null || ingredientes.isEmpty()) {
            return false;
        }
        if (estado != EstadoProducto.ACTIVO) {
            return false;
        }
        for (ProductoIngrediente pi : ingredientes) {
            if (pi.getIngrediente().getStock() < pi.getCantidad()) {
                return false;
            }
        }
        return true;
    }

    // GETTERS Y SETTERS
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
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

    public TipoProducto getTipo() {
        return tipo;
    }

    public void setTipo(TipoProducto tipo) {
        this.tipo = tipo;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public void setEstado(EstadoProducto estado) {
        this.estado = estado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<ProductoIngrediente> getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(List<ProductoIngrediente> ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Producto)) {
            return false;
        }
        Producto other = (Producto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Producto[ id=" + id + " ]";
    }
}
