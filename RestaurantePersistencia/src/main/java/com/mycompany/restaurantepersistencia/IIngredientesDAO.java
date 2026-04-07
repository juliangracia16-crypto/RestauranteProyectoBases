
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedtos.ActualizarIngredienteDTO;
import com.mycompany.restaurantedtos.BuscadorIngredientesDTO;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;
import java.util.List;

/**
 *
 * @author Julian
 */
public interface IIngredientesDAO {
    public abstract Ingrediente registrarIngrediente(NuevoIngredienteDTO ingrediente) throws PersistenciaException;
    public abstract Ingrediente agregarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws PersistenciaException;
    public abstract Ingrediente quitarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws PersistenciaException;
    public abstract Ingrediente consultarIngredientePorId(Long id) throws PersistenciaException;
    public abstract Ingrediente consultarIngredienteRegistrado(NuevoIngredienteDTO ingrediente) throws PersistenciaException;
    public abstract List<Ingrediente> consultarIngredientes() throws PersistenciaException;
    public abstract List<Ingrediente> consultarIngredientesFiltrados(BuscadorIngredientesDTO ingredienteFiltrado) throws PersistenciaException;
    public abstract Ingrediente eliminarIngrediente(Long id) throws PersistenciaException;
}
