
package com.mycompany.restaurantepersistencia.adapters;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;
import com.mycompany.restaurantedtos.UnidadMedida;

/**
 *
 * @author Julian
 */
public class IngredienteDominioAIngredienteDTOAdapter {
     public static NuevoIngredienteDTO adaptar(Ingrediente nuevoIngrediente){
        UnidadMedida unidadMedida = UnidadMedida.PIEZA;
        if(null!=  nuevoIngrediente.getUnidadMedida()){
            switch (nuevoIngrediente.getUnidadMedida()) {
                case GRAMOS -> unidadMedida = UnidadMedida.GRAMOS;
                case MILILITRO -> unidadMedida = UnidadMedida.MILILITRO;
                case PIEZA -> unidadMedida = UnidadMedida.PIEZA;
            }
        }
        NuevoIngredienteDTO ingrediente = new NuevoIngredienteDTO(nuevoIngrediente.getNombre(),nuevoIngrediente.getStock(),unidadMedida);
        return ingrediente;
    }
}
