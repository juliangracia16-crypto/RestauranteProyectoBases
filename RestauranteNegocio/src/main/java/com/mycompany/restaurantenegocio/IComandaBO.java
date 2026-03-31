/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedtos.ComandaDTO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IComandaBO {
    
    public Comanda crear(ComandaDTO comanda) throws NegocioException;

    public abstract Comanda actualizar(Long id, ComandaDTO comanda) throws NegocioException;

    public abstract Comanda buscarPorId(Long id) throws NegocioException;

    public abstract Comanda buscarPorFolio(String folio) throws NegocioException;

    public abstract List<Comanda> obtenerTodos() throws NegocioException;

    public abstract List<Comanda> buscarPorRangoFechas(LocalDateTime inicio, LocalDateTime fin) throws NegocioException;

    public abstract Comanda cancelar(Long id) throws NegocioException;

    public abstract Comanda entregar(Long id) throws NegocioException;
    
}
