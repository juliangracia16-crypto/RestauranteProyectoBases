/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedominio.ProductoSeleccionado;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;

/**
 * Implementación de {@link IProductoSeleccionadoDAO} utilizando JPA.
 * 
 * Esta clase gestiona la persistencia de los productos seleccionados
 * dentro de una comanda.
 * 
 * Funcionalidades:
 * - Agregar productos seleccionados a una comanda
 * - Eliminar productos seleccionados
 * - Consultar productos seleccionados por comanda
 * 
 */
public class ProductoSeleccionadoDAO implements IProductoSeleccionadoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductoSeleccionadoDAO.class.getName());
    
    
    
    /**
     * Agrega un producto seleccionado a la base de datos.
     * 
     * 1. Se inicia una transacción
     * 2. Se persiste el objeto {@link ProductoSeleccionado}
     * 3. Se confirma la transacción
     * 
     * @param ps producto seleccionado a persistir
     * @return el producto seleccionado persistido
     * @throws PersistenciaException si ocurre un error durante la operación
     */
    @Override
    public ProductoSeleccionado agregar(ProductoSeleccionado ps) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();
            em.persist(ps);
            em.getTransaction().commit();
            return ps;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo agregar el producto a la comanda.", ex);
        }
    }


    /**
     * Elimina un producto seleccionado por su ID.
     * 
     * 1. Se busca el objeto en la base de datos
     * 2. Si existe, se elimina
     * 3. Se confirma la transacción
     * 
     * @param id identificador del producto seleccionado
     * @throws PersistenciaException si ocurre un error durante la eliminación
     */
    @Override
    public void eliminar(Long id) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();
            ProductoSeleccionado ps = em.find(ProductoSeleccionado.class, id);
            if (ps != null) {
                em.remove(ps);
            }
            em.getTransaction().commit();
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo eliminar el producto de la comanda.", ex);
        }
    }

    /**
     * Obtiene todos los productos seleccionados asociados a una comanda.
     * 
     * @param idComanda identificador de la comanda
     * @return lista de productos seleccionados (puede estar vacía pero nunca null)
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    @Override
    public List<ProductoSeleccionado> obtenerPorComanda(Long idComanda) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            return em.createQuery(
                "SELECT ps FROM ProductoSeleccionado ps WHERE ps.comanda.id = :idComanda",ProductoSeleccionado.class).setParameter("idComanda", idComanda).getResultList();
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron obtener los productos de la comanda.", ex);
        }
    }
    
}
