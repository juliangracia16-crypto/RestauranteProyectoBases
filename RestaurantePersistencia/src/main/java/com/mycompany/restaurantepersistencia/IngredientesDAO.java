
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedtos.ActualizarIngredienteDTO;
import com.mycompany.restaurantedtos.BuscadorIngredientesDTO;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;
import com.mycompany.restaurantepersistencia.adapters.BuscadorIngredientesDTOAIngredienteAdapter;
import com.mycompany.restaurantepersistencia.adapters.NuevoIngredienteDTOAIngredienteAdapter;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

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

    @Override
    public Ingrediente consultarIngredientePorId(Long id) throws PersistenciaException {
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            Ingrediente ingredienteEncontrado = entityManager.find(Ingrediente.class, id);
            entityManager.getTransaction().commit();
            return ingredienteEncontrado;
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo consultar el ingrediente.",ex);
        }
    }

    @Override
    public Ingrediente consultarIngredienteRegistrado(NuevoIngredienteDTO ingrediente) throws PersistenciaException {
        Ingrediente ingredienteAdaptado = NuevoIngredienteDTOAIngredienteAdapter.adaptar(ingrediente);
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            if (ingredienteAdaptado.getNombre() == null || ingredienteAdaptado.getNombre().trim().isEmpty() || ingredienteAdaptado.getUnidadMedida() == null) {
                return null;
            }
            String jpql = "SELECT i " +
                  "FROM Ingrediente i " +
                  "WHERE LOWER(TRIM(i.nombre)) = LOWER(:nombre) " +
                  "AND i.unidadMedida = :unidadMedida";
            
            List<Ingrediente> resultados = entityManager.createQuery(jpql, Ingrediente.class)
            .setParameter("nombre", ingredienteAdaptado.getNombre().trim())
            .setParameter("unidadMedida", ingredienteAdaptado.getUnidadMedida())
            .getResultList();

            return resultados.isEmpty() ? null : resultados.get(0);
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo consultar si se encuentra registrado el ingrediente.",ex);
        }
    }
    
    @Override        
    public List<Ingrediente> consultarIngredientes() throws PersistenciaException{
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            String jpql = "SELECT i FROM Ingrediente i";
            List<Ingrediente> ingredientes = entityManager.createQuery(jpql, Ingrediente.class).getResultList();
            return ingredientes;
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron consultar todos los ingredientes.",ex);
        }
    }

    @Override
    public List<Ingrediente> consultarIngredientesFiltrados(BuscadorIngredientesDTO ingredienteFiltrado) throws PersistenciaException {
        Ingrediente ingredienteFiltradoAdaptado = BuscadorIngredientesDTOAIngredienteAdapter.adaptar(ingredienteFiltrado);
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            StringBuilder jpql = new StringBuilder("SELECT i FROM Ingrediente i WHERE 1=1");

            if (ingredienteFiltradoAdaptado.getNombre() != null && !ingredienteFiltradoAdaptado.getNombre().trim().isEmpty()) {
                jpql.append(" AND LOWER(i.nombre) LIKE LOWER(:nombre)");
            }

            if (ingredienteFiltradoAdaptado.getUnidadMedida() != null) {
                jpql.append(" AND i.unidadMedida = :unidadMedida");
            }

            TypedQuery<Ingrediente> query = entityManager.createQuery(jpql.toString(), Ingrediente.class);

            if (ingredienteFiltradoAdaptado.getNombre() != null && !ingredienteFiltradoAdaptado.getNombre().trim().isEmpty()) {
                query.setParameter("nombre", "%" + ingredienteFiltradoAdaptado.getNombre().trim() + "%");
            }
            if (ingredienteFiltradoAdaptado.getUnidadMedida() != null) {
                query.setParameter("unidadMedida", ingredienteFiltradoAdaptado.getUnidadMedida());
            }
            return query.getResultList();
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron consultar los ingredientes correctamente.",ex);
        }
    }

    @Override
    public Ingrediente eliminarIngrediente(Long id) throws PersistenciaException {
        try{
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            Ingrediente ingredienteEncontrado = entityManager.find(Ingrediente.class, id);
            entityManager.remove(ingredienteEncontrado);
            entityManager.getTransaction().commit();
            return ingredienteEncontrado;
        }catch(PersistenceException ex){
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se eliminar el ingrediente correctamente.",ex);
        }
    }

}
