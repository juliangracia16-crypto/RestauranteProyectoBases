
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedtos.ActualizarIngredienteDTO;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;

/**
 *
 * @author Julian
 */
public interface IIngredientesDAO {
    public abstract Ingrediente registrarIngrediente(NuevoIngredienteDTO ingrediente) throws PersistenciaException;
    public abstract Ingrediente agregarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws PersistenciaException;
    public abstract Ingrediente quitarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws PersistenciaException;
    
}
