
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;
import com.mycompany.restaurantepersistencia.adapters.NuevoIngredienteDTOAIngredienteAdapter;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 *
 * @author Julian
 */
public class IngredientesDAO implements IIngredientesDAO{
    private static final Logger LOGGER = Logger.getLogger(IngredientesDAO.class.getName());

    @Override
    public Ingrediente registrarIngrediente(NuevoIngredienteDTO ingrediente) throws PersistenciaException {
        Ingrediente ingredienteAdaptado = NuevoIngredienteDTOAIngredienteAdapter.adaptar(ingrediente);
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(ingredienteAdaptado);
            entityManager.getTransaction().commit();
            return ingredienteAdaptado;
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo registrar el ingrediente.",ex);
        }
    }
    
}
