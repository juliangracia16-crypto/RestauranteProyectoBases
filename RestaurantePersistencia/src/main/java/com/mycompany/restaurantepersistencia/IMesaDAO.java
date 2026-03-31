/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Mesa;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IMesaDAO {
    
    public abstract void insertarMesasMasivo() throws PersistenciaException;
    
    public abstract Mesa buscarPorId(Long id) throws PersistenciaException;
    
    public abstract List<Mesa> obtenerTodos() throws PersistenciaException;

    public abstract List<Mesa> obtenerMesasLibres() throws PersistenciaException;

    public abstract Mesa actualizarDisponibilidad(Long id) throws PersistenciaException;
    
}
