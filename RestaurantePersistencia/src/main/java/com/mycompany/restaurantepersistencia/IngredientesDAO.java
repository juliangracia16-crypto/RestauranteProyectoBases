
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedtos.ActualizarIngredienteDTO;
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

    @Override
    public Ingrediente agregarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws PersistenciaException {
        
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            Ingrediente ingredienteActualizar = entityManager.find(Ingrediente.class, ingrediente.getIdIngrediente());
            Integer cantidadTotalStock = ingrediente.getCantidad() + ingredienteActualizar.getStock();
            ingredienteActualizar.setStock(cantidadTotalStock);
            entityManager.persist(ingredienteActualizar);
            entityManager.getTransaction().commit();
            return ingredienteActualizar;
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo agregar el stock al ingrediente.",ex);
        }
    }
    
    @Override
    public Ingrediente quitarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws PersistenciaException {
        
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            Ingrediente ingredienteActualizar = entityManager.find(Ingrediente.class, ingrediente.getIdIngrediente());
            Integer cantidadTotalStock = ingredienteActualizar.getStock() - ingrediente.getCantidad();
            if(cantidadTotalStock < 0){
                throw new PersistenciaException("No se puede quitar mas de lo que se tiene.",null);
            }
            ingredienteActualizar.setStock(cantidadTotalStock);
            entityManager.persist(ingredienteActualizar);
            entityManager.getTransaction().commit();
            return ingredienteActualizar;
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo agregar el stock al ingrediente.",ex);
        }
    }
}
