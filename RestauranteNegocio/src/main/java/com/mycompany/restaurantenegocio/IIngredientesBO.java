
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedtos.ActualizarIngredienteDTO;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;

/**
 *
 * @author Julian
 */
public interface IIngredientesBO {
    public abstract Ingrediente registrarIngrediente(NuevoIngredienteDTO ingrediente) throws NegocioException;
    public abstract Ingrediente agregarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws NegocioException;
    public abstract Ingrediente quitarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws NegocioException;
    public abstract Ingrediente consultarIngredientePorId(Long id) throws NegocioException;
}
