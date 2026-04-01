
package com.mycompany.restaurantedtos;

/**
 *
 * @author Julian
 */
public class ActualizarIngredienteDTO {
    private Long idIngrediente;
    private Integer cantidad;

    public ActualizarIngredienteDTO(Long idIngrediente, Integer cantidad) {
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public Integer getCantidad() {
        return cantidad;
    }
    
    
}
