/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedominio.ClienteGeneral;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedtos.ClienteFrecuenteDTO;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ClienteDAO implements IClienteDAO{
    
    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    @Override
    public ClienteFrecuente crear(ClienteFrecuenteDTO cliente) throws PersistenciaException {
        ClienteFrecuente nuevoCliente = new ClienteFrecuente(cliente.getNombre(), cliente.getTelefono(), cliente.getCorreo());
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            entityManager.persist(nuevoCliente);
            entityManager.getTransaction().commit();
            return nuevoCliente;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo guardar el cliente", ex);
        }  
    }

    @Override
    public ClienteFrecuente actualizar(ClienteFrecuenteDTO cliente) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            ClienteFrecuente clienteGuardado = entityManager.find(ClienteFrecuente.class, cliente.getIdCliente());
            clienteGuardado.setNombre(cliente.getNombre());
            clienteGuardado.setTelefono(cliente.getTelefono());
            clienteGuardado.setCorreo(cliente.getCorreo());
            entityManager.persist(clienteGuardado);
            entityManager.getTransaction().commit();
            return clienteGuardado;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo actualizar el cliente", ex);
        }
    }
    
    @Override
    public ClienteFrecuente eliminar(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            ClienteFrecuente cliente = entityManager.find(ClienteFrecuente.class, id);
            entityManager.remove(cliente);
            entityManager.getTransaction().commit();
            return cliente;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo eliminar el cliente", ex);
        }
    }

    @Override
    public ClienteFrecuente buscarPorId(Long id) throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();
            ClienteFrecuente cliente = entityManager.find(ClienteFrecuente.class, id);
            entityManager.getTransaction().commit();
            return cliente;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo encontrar el cliente con id: " + id, ex);
        }
    }

    @Override
    public List<ClienteFrecuente> obtenerTodos() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();
            entityManager.getTransaction().begin();

            String jpql = "SELECT cf FROM ClienteFrecuente cf";
            TypedQuery<ClienteFrecuente> query = entityManager.createQuery(jpql, ClienteFrecuente.class);
            List<ClienteFrecuente> clientes = query.getResultList();

            entityManager.getTransaction().commit();
            return clientes;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron obtener los clientes", ex);
        }
    }
    
    @Override
    public ClienteGeneral crear() throws PersistenciaException {
        try {
            EntityManager entityManager = ManejadorConexiones.crearEntityManager();

            // Si ya existe lo retorna
            String jpql = "SELECT cg FROM ClienteGeneral cg";
            List<ClienteGeneral> lista = entityManager
                    .createQuery(jpql, ClienteGeneral.class)
                    .getResultList();
            if (!lista.isEmpty()) {
                return lista.get(0);
            }

            // Si no existe lo crea
            ClienteGeneral cg = new ClienteGeneral();
            entityManager.getTransaction().begin();
            entityManager.persist(cg);
            entityManager.getTransaction().commit();
            return cg;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo registrar el Cliente General", ex);
        }
    }

    
    
}
