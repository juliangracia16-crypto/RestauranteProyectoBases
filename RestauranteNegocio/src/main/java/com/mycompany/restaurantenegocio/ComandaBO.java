/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedominio.EstadoComanda;
import com.mycompany.restaurantedtos.ComandaDTO;
import com.mycompany.restaurantepersistencia.IComandaDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ComandaBO implements IComandaBO {
    
    private final IComandaDAO comandaDAO;

    public ComandaBO(IComandaDAO comandaDAO) {
        this.comandaDAO = comandaDAO;
    }

    @Override
    public Comanda crear(ComandaDTO comanda) throws NegocioException {
        try {
            if (comanda.getIdMesa() == null) {
                throw new NegocioException("La mesa es obligatoria.");
            }
            if (comanda.getIdCliente() == null) {
                throw new NegocioException("El cliente es obligatorio.");
            }
        
            Comanda nuevaComanda = comandaDAO.crear(comanda);
            return nuevaComanda;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar crear la comanda.", ex);
        }
    }

    @Override
    public Comanda actualizar(Long id, ComandaDTO comanda) throws NegocioException {
        try{
            if (id == null) {
                throw new NegocioException("El ID de la comanda no puede ser nulo.");
            }
            if (id <= 0) {
                throw new NegocioException("El ID de la comanda debe ser un numero positivo.");
            }
            if (comanda.getTotal() == null) {
                throw new NegocioException("El total de la comanda no puede ser nulo.");
            }
            if (comanda.getTotal() < 0) {
                throw new NegocioException("El total de la comanda no puede ser negativo.");
            }
            // Verificamos que la comanda no este Entregada o Cancelada
            Comanda comandaExistente = comandaDAO.buscarPorId(id);
            if (comandaExistente == null) {
                throw new NegocioException("No existe una comanda con ID: " + id);
            }
            if (comandaExistente.getEstado() == EstadoComanda.ENTREGADA) {
                throw new NegocioException("No se puede modificar una comanda Entregada.");
            }
            if (comandaExistente.getEstado() == EstadoComanda.CANCELADA) {
                throw new NegocioException("No se puede modificar una comanda Cancelada.");
            }
            return comandaDAO.actualizar(id, comanda);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar actualizar la comanda.", ex);
        }
    }

    @Override
    public Comanda buscarPorId(Long id) throws NegocioException {
        try {
            if (id == null) {
                throw new NegocioException("El ID de la comanda no puede ser nulo.");
            }
            if (id <= 0) {
                throw new NegocioException("El ID de la comanda debe ser un numero positivo.");
            }
        
            Comanda comanda = comandaDAO.buscarPorId(id);
            return comanda;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar buscar la comanda: " + id, ex);
        }
    }

    @Override
    public Comanda buscarPorFolio(String folio) throws NegocioException {
        try {
            if (folio == null || folio.isBlank()) {
                throw new NegocioException("El folio no puede ser nulo o vacio.");
            }
        
            Comanda comanda = comandaDAO.buscarPorFolio(folio);
            return comanda;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar buscar la comanda con folio: " + folio, ex);
        }
    }

    @Override
    public List<Comanda> obtenerTodos() throws NegocioException {
        try {
            List<Comanda> comandas = comandaDAO.obtenerTodos();
            return comandas;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar obtener todas las comandas.", ex);
        }
    }

    @Override
    public List<Comanda> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) throws NegocioException {
        try {
            if (inicio == null) {
                throw new NegocioException("La fecha de inicio no puede ser nula.");
            }
            if (fin == null) {
                throw new NegocioException("La fecha de fin no puede ser nula.");
            }
            if (inicio.isAfter(fin)) {
                throw new NegocioException("La fecha de inicio no puede ser mayor a la fecha de fin.");
            }
        
            List<Comanda> comandas = comandaDAO.buscarPorRangoFechas(inicio, fin);
            return comandas;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar buscar las comandas por rango de fechas.", ex);
        }
    }

    @Override
    public Comanda cancelar(Long id) throws NegocioException {
        try {
            if (id == null) {
                throw new NegocioException("El ID de la comanda no puede ser nulo.");
            }
            if (id <= 0) {
                throw new NegocioException("El ID de la comanda debe ser un numero positivo.");
            }
        
            Comanda comanda = comandaDAO.buscarPorId(id);
            if (comanda == null) {
                throw new NegocioException("No existe una comanda con ID: " + id);
            }
            if (comanda.getEstado() == EstadoComanda.ENTREGADA) {
                throw new NegocioException("No se puede cancelar una comanda ya Entregada.");
            }
            if (comanda.getEstado() == EstadoComanda.CANCELADA) {
                throw new NegocioException("La comanda ya esta Cancelada.");
            }
            return comandaDAO.cancelar(id);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar cancelar la comanda.", ex);
        }
    }

    @Override
    public Comanda entregar(Long id) throws NegocioException {
        try {
            if (id == null) {
                throw new NegocioException("El ID de la comanda no puede ser nulo.");
            }
            if (id <= 0) {
                throw new NegocioException("El ID de la comanda debe ser un numero positivo.");
            }
        
            Comanda comanda = comandaDAO.buscarPorId(id);
            if (comanda == null) {
                throw new NegocioException("No existe una comanda con ID: " + id);
            }
            if (comanda.getEstado() == EstadoComanda.CANCELADA) {
                throw new NegocioException("No se puede entregar una comanda Cancelada.");
            }
            if (comanda.getEstado() == EstadoComanda.ENTREGADA) {
                throw new NegocioException("La comanda ya esta Entregada.");
            }
            return comandaDAO.entregar(id);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al intentar entregar la comanda.", ex);
        }
    }
    
}
