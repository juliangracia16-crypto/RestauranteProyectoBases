package com.mycompany.restaurantedtos;


/**
 * DTO que representa la relación
 * entre un producto y un ingrediente.
 * 
 * Contiene:
 * - ID del ingrediente
 * - Cantidad requerida de dicho ingrediente
 * 
 */
public class ProductoIngredienteDTO {

    private Long idIngrediente;
    private Integer cantidad;

    /**
     * Constructor vacío.
     */
    public ProductoIngredienteDTO() {
    }
    
    /**
     * Constructor con parámetros.
     * 
     * @param idIngrediente identificador del ingrediente
     * @param cantidad cantidad requerida del ingrediente
     */
    public ProductoIngredienteDTO(Long idIngrediente, Integer cantidad) {
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }

    /**
     * Obtiene el ID del ingrediente.
     * 
     * @return identificador del ingrediente
     */
    public Long getIdIngrediente() {
        return idIngrediente;
    }

    /**
     * Obtiene la cantidad del ingrediente.
     * 
     * @return cantidad requerida
     */
    public Integer getCantidad() {
        return cantidad;
    }
}
