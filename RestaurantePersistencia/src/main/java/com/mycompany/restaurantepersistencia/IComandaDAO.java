/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedtos.ComandaDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IComandaDAO {
    
    public Comanda crear(ComandaDTO comanda) throws PersistenciaException;

    public abstract Comanda actualizar(Long id, ComandaDTO comanda) throws PersistenciaException;

    public abstract Comanda buscarPorId(Long id) throws PersistenciaException;

    public abstract Comanda buscarPorFolio(String folio) throws PersistenciaException;

    public abstract List<Comanda> obtenerTodos() throws PersistenciaException;

    public abstract List<Comanda> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) throws PersistenciaException;

    public abstract Comanda cancelar(Long id) throws PersistenciaException;

    public abstract Comanda entregar(Long id) throws PersistenciaException;
    
}
