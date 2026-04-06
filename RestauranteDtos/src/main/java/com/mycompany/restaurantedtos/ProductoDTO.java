package com.mycompany.restaurantedtos;
import java.util.List;

public class ProductoDTO {

    private Long idProducto;
    private String nombre;
    private String descripcion;
    private Double precio;
    private TipoProducto tipo;
    private EstadoProducto estado;
    private byte[] imagen;
    private List<ProductoIngredienteDTO> ingredientes;

    public ProductoDTO() {
    }

    // CONSTRUCTOR MODO NUEVO
    public ProductoDTO(String nombre, String descripcion, Double precio,
            TipoProducto tipo, List<ProductoIngredienteDTO> ingredientes) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.precio = precio;
        this.tipo = tipo;
        this.ingredientes = ingredientes;
    }

    // CONSTRUCTOR PARA EL MODO EDITAR
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
