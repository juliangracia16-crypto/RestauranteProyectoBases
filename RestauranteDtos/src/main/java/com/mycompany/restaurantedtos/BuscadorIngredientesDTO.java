
package com.mycompany.restaurantedtos;

/**
 *
 * @author Julian
 */
public class BuscadorIngredientesDTO {
    String nombre;
    UnidadMedida unidadMedida;

    public BuscadorIngredientesDTO(String nombre, UnidadMedida unidadMedida) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }
    
}
