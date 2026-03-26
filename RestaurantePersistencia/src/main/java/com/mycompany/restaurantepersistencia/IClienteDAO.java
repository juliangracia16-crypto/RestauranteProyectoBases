/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedominio.ClienteGeneral;
import com.mycompany.restaurantedtos.ClienteFrecuenteDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IClienteDAO {
    
    public abstract ClienteFrecuente crear(ClienteFrecuenteDTO cliente) throws PersistenciaException;

    public abstract ClienteFrecuente actualizar(ClienteFrecuenteDTO cliente) throws PersistenciaException;
    
    public abstract ClienteFrecuente eliminar(Long id) throws PersistenciaException;

    public abstract ClienteFrecuente buscarPorId(Long id) throws PersistenciaException;

    public abstract List<ClienteFrecuente> obtenerTodos() throws PersistenciaException;
    
    public abstract ClienteGeneral crear() throws PersistenciaException;
    
}
