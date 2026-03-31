/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.DisponibilidadMesa;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedominio.Mesa;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class MesaDAO implements IMesaDAO {
    
    private static final Logger LOGGER = Logger.getLogger(MesaDAO.class.getName());
    
    @Override
    public void insertarMesasMasivo() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            // Verificamos si ya existen mesas
            String jpql = "SELECT COUNT(m) FROM Mesa m";
            Long count = entityManager.createQuery(jpql, Long.class).getSingleResult();

            if (count > 0) {
                entityManager.getTransaction().commit();
                return;
            }
            // Insertamos 20 mesas todas LIBRES
            for (Integer i = 1; i <= 20; i++) {
                Mesa mesa = new Mesa(i, DisponibilidadMesa.LIBRE);
                entityManager.persist(mesa);
            }
            entityManager.getTransaction().commit();
        }catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron insertar las mesas", ex);
        }
    }
    
    @Override
    public Mesa buscarPorId(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            Mesa mesa = entityManager.find(Mesa.class, id);
            entityManager.getTransaction().commit();
            return mesa;
        }catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo encontrar la mesa con id: " + id, ex);
        }
    }

    @Override
    public List<Mesa> obtenerTodos() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            String jpql = "SELECT m FROM Mesa m";
            TypedQuery<Mesa> query = entityManager.createQuery(jpql, Mesa.class);
            List<Mesa> mesas = query.getResultList();

            entityManager.getTransaction().commit();
            return mesas;
        }catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron obtener las mesas", ex);
        }
    }

    @Override
    public List<Mesa> obtenerMesasLibres() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            String jpql = "SELECT m FROM Mesa m WHERE m.disponibilidad = :disponibilidad";
            TypedQuery<Mesa> query = entityManager.createQuery(jpql, Mesa.class);
            query.setParameter("disponibilidad", DisponibilidadMesa.LIBRE);
            List<Mesa> mesas = query.getResultList();

            entityManager.getTransaction().commit();
            return mesas;
        }catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron obtener las mesas libres", ex);
        }
    }

    @Override
    public Mesa actualizarDisponibilidad(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            Mesa mesa = entityManager.find(Mesa.class, id);

            // Forzamos leer el estado actual de la BD
            entityManager.refresh(mesa);

            if (mesa.getDisponibilidad() == DisponibilidadMesa.LIBRE) {
                mesa.setDisponibilidad(DisponibilidadMesa.OCUPADA);
            } else {
                mesa.setDisponibilidad(DisponibilidadMesa.LIBRE);
            }
            entityManager.persist(mesa);
            entityManager.getTransaction().commit();
            return mesa;
        }catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo actualizar la disponibilidad de la mesa", ex);
        }
    }
    
}
