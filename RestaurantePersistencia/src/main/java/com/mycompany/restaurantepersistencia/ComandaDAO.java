/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Cliente;
import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedominio.EstadoComanda;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedominio.Mesa;
import com.mycompany.restaurantedtos.ComandaDTO;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ComandaDAO implements IComandaDAO {
    
    private static final Logger LOGGER = Logger.getLogger(ComandaDAO.class.getName());

    @Override
    public Comanda crear(ComandaDTO comanda) throws PersistenciaException {
        //Generamos el folio automatico OB-YYYYMMDD-XXX
        LocalDateTime ahora = LocalDateTime.now();
        String fecha = ahora.format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();

            //Buscamos mesa y cliente
            Mesa mesa = entityManager.find(Mesa.class, comanda.getIdMesa());
            Cliente cliente = entityManager.find(Cliente.class, comanda.getIdCliente());

            //Contamos comandas de hoy para el consecutivo
            String jpqlCount = "SELECT COUNT(c) FROM Comanda c WHERE c.folio LIKE :prefijo";
            Long count = entityManager.createQuery(jpqlCount, Long.class).setParameter("prefijo", "OB-" + fecha + "%").getSingleResult();
            String folio = "OB-" + fecha + "-" + String.format("%03d", count + 1);

            // Convertimos DTO a entidad
            Comanda nuevaComanda = new Comanda(folio, ahora, 0.0, EstadoComanda.ABIERTA, mesa, cliente);

            entityManager.getTransaction().begin();
            entityManager.persist(nuevaComanda);
            entityManager.getTransaction().commit();
            return nuevaComanda;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo crear la comanda", ex);
        }
    }

    @Override
    public Comanda actualizar(Long id, ComandaDTO comanda) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            Comanda comandaGuardada = entityManager.find(Comanda.class, id);
            comandaGuardada.setTotal(comanda.getTotal());

            entityManager.persist(comandaGuardada);
            entityManager.getTransaction().commit();
            return comandaGuardada;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo actualizar la comanda", ex);
        }
    }

    @Override
    public Comanda buscarPorId(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            Comanda comanda = entityManager.find(Comanda.class, id);
            entityManager.getTransaction().commit();
            return comanda;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo encontrar la comanda con id: " + id, ex);
        }
    }

    @Override
    public Comanda buscarPorFolio(String folio) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            String jpql = "SELECT c FROM Comanda c WHERE c.folio = :folio";
            TypedQuery<Comanda> query = entityManager.createQuery(jpql, Comanda.class);
            query.setParameter("folio", folio);
            Comanda comanda = query.getSingleResult();

            entityManager.getTransaction().commit();
            return comanda;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo encontrar la comanda con folio: " + folio, ex);
        }
    }

    @Override
    public List<Comanda> obtenerTodos() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            String jpql = "SELECT c FROM Comanda c";
            TypedQuery<Comanda> query = entityManager.createQuery(jpql, Comanda.class);
            List<Comanda> comandas = query.getResultList();

            entityManager.getTransaction().commit();
            return comandas;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron obtener las comandas", ex);
        }
    }

    @Override
    public List<Comanda> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            String jpql = "SELECT c FROM Comanda c " + "WHERE c.fechaHora BETWEEN :inicio AND :fin";
            TypedQuery<Comanda> query = entityManager.createQuery(jpql, Comanda.class);
            query.setParameter("inicio", inicio);
            query.setParameter("fin", fin);
            List<Comanda> comandas = query.getResultList();

            entityManager.getTransaction().commit();
            return comandas;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron buscar las comandas por rango de fechas", ex);
        }
    }

    @Override
    public Comanda cancelar(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            Comanda comanda = entityManager.find(Comanda.class, id);
            comanda.setEstado(EstadoComanda.CANCELADA);
            entityManager.persist(comanda);
            entityManager.getTransaction().commit();
            return comanda;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo cancelar la comanda", ex);
        }
    }

    @Override
    public Comanda entregar(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            Comanda comanda = entityManager.find(Comanda.class, id);
            comanda.setEstado(EstadoComanda.ENTREGADA);
            entityManager.persist(comanda);
            entityManager.getTransaction().commit();
            return comanda;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo entregar la comanda", ex);
        }
    }
    
}
