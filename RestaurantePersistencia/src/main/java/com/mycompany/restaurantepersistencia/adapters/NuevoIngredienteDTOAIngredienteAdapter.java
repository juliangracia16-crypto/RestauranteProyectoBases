
package com.mycompany.restaurantepersistencia.adapters;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.UnidadMedida;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;

/**
 *
 * @author Julian
 */
public class NuevoIngredienteDTOAIngredienteAdapter {
    
    public static Ingrediente adaptar(NuevoIngredienteDTO nuevoIngrediente){
        UnidadMedida unidadMedida = UnidadMedida.OTRO;
        if(null!=  nuevoIngrediente.getUnidadMedida()){
            switch (nuevoIngrediente.getUnidadMedida()) {
                case GRAMOS -> unidadMedida = UnidadMedida.GRAMOS;
                case KILOGRAMO -> unidadMedida = UnidadMedida.KILOGRAMO;
                case LATA -> unidadMedida = UnidadMedida.LATA;
                case LITRO -> unidadMedida = UnidadMedida.LITRO;
                case MILILITRO -> unidadMedida = UnidadMedida.MILILITRO;
                case PAQUETE -> unidadMedida = UnidadMedida.PAQUETE;
                case PIEZA -> unidadMedida = UnidadMedida.PIEZA;
                case SOBRE -> unidadMedida = UnidadMedida.SOBRE;
                default -> unidadMedida = UnidadMedida.OTRO;
            }
        }
        Ingrediente ingrediente = new Ingrediente(nuevoIngrediente.getNombre(),nuevoIngrediente.getStockInicial(),unidadMedida);
        return ingrediente;
    }
}
