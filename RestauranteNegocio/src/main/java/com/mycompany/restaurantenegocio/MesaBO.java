/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Mesa;
import com.mycompany.restaurantepersistencia.IMesaDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class MesaBO implements IMesaBO{
    
    private final IMesaDAO mesaDAO;

    public MesaBO(IMesaDAO mesaDAO) {
        this.mesaDAO = mesaDAO;
    }

    @Override
    public void insertarMesasMasivo() throws NegocioException {
        try {
            mesaDAO.insertarMesasMasivo();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar insertar las mesas ", ex);
        }
    }

    @Override
    public Mesa buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID de la mesa no puede ser nulo ");
        }
        if (id <= 0) {
            throw new NegocioException("El ID de la mesa debe ser un numero positivo ");
        }
        try {
            Mesa mesa = mesaDAO.buscarPorId(id);
            return mesa;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar buscar la mesa: " + id, ex);
        }
    }

    @Override
    public List<Mesa> obtenerTodos() throws NegocioException {
        try {
            List<Mesa> mesas = mesaDAO.obtenerTodos();
            return mesas;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar obtener todas las mesas ", ex);
        }
    }

    @Override
    public List<Mesa> obtenerMesasLibres() throws NegocioException {
        try {
            List<Mesa> mesas = mesaDAO.obtenerMesasLibres();
            return mesas;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar obtener las mesas libres ", ex);
        }
    }

    @Override
    public Mesa actualizarDisponibilidad(Long id) throws NegocioException {
        try {
            if (id == null) {
                throw new NegocioException("El ID de la mesa no puede ser nulo ");
            }
            if (id <= 0) {
                throw new NegocioException("El ID de la mesa debe ser un numero positivo ");
            }
            Mesa mesa = mesaDAO.buscarPorId(id);
            if (mesa == null) {
                throw new NegocioException("No existe una mesa con ID: " + id);
            }
            return mesaDAO.actualizarDisponibilidad(id);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar actualizar la disponibilidad de la mesa ", ex);
        }
    }
    
}
