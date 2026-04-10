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
 *
 * @author PC GAMER MASTER RACE
 */
public class ProductoSeleccionadoDAO implements IProductoSeleccionadoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductoSeleccionadoDAO.class.getName());
    
    

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
