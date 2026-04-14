package com.mycompany.restaurantedtos;
import java.util.List;


/**
 * DTO que representa un producto dentro del sistema.
 * 

 *  * Esta clase se utiliza para transportar datos entre las capas.
 * 
 * Contiene la información necesaria para:
 * - Registrar un producto
 * - Actualizar un producto
 * - Transferir datos de producto sin exponer directamente la entidad
 * 
 * Incluye:
 * - Datos básicos (nombre, descripción, precio)
 * - Tipo y estado del producto
 * - Imagen en formato binario
 * - Lista de ingredientes asociados
 */
public class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private TipoProducto tipo;
    private EstadoProducto estado;
    private byte[] imagen;
    private List<ProductoIngredienteDTO> ingredientes;

    /**
     * Constructor vacío.
     * 
     */
    public ProductoDTO() {
    }

    /**
     * Constructor para registrar un nuevo producto.
     * 
     * 
     * @param nombre nombre del producto
     * @param descripcion descripción del producto
     * @param precio precio del producto
     * @param tipo tipo de producto
     * @param ingredientes lista de ingredientes asociados
     */
    public ProductoDTO(String nombre, String descripcion, Double precio,
            TipoProducto tipo, List<ProductoIngredienteDTO> ingredientes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.ingredientes = ingredientes;
    }

    /**
     * Constructor para actualizar un producto existente.
     * 
     * 
     * @param idProducto identificador del producto
     * @param nombre nombre del producto
     * @param descripcion descripción del producto
     * @param precio precio del producto
     * @param tipo tipo de producto
     * @param estado estado actual del producto
     * @param ingredientes lista de ingredientes asociados
     */
    public ProductoDTO(Long idProducto, String nombre, String descripcion, Double precio,
            TipoProducto tipo, EstadoProducto estado, List<ProductoIngredienteDTO> ingredientes) {
        this.idProducto = idProducto;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.estado = estado;
        this.ingredientes = ingredientes;
    }

    // GETTERS Y SETTERS
    public Long getIdProducto() {
        return idProducto;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public Double getPrecio() {
        return precio;
    }

    public TipoProducto getTipo() {
        return tipo;
    }

    public EstadoProducto getEstado() {
        return estado;
    }

    public byte[] getImagen() {
        return imagen;
    }

    // El byte lo estoy usando para que luego almacenemos las imagenes directamente en la bd, falta revisar como se van a manejar las imgs al final.
    // Hay que preguntarle al profe pibes
    public void setImagen(byte[] imagen) {
        this.imagen = imagen;
    }

    public List<ProductoIngredienteDTO> getIngredientes() {
        return ingredientes;
    }
}
