
package com.mycompany.restaurantepersistencia.adapters;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.UnidadMedida;
import com.mycompany.restaurantedtos.BuscadorIngredientesDTO;

/**
 * Clase para adaptar la unidad de medida DTO
 * a unidad de medida DOMINIO
 * @author Julian
 */
public class BuscadorIngredientesDTOAIngredienteAdapter {
    public static Ingrediente adaptar(BuscadorIngredientesDTO nuevoIngrediente){
        UnidadMedida unidadMedida = UnidadMedida.PIEZA;
        if(null!=  nuevoIngrediente.getUnidadMedida()){
            switch (nuevoIngrediente.getUnidadMedida()) {
                case GRAMOS -> unidadMedida = UnidadMedida.GRAMOS;
                case MILILITRO -> unidadMedida = UnidadMedida.MILILITRO;
                case PIEZA -> unidadMedida = UnidadMedida.PIEZA;
            }
        }
        Ingrediente ingrediente = new Ingrediente(nuevoIngrediente.getNombre(),unidadMedida);
        return ingrediente;
    }
}
