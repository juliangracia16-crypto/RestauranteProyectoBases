/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Mesa;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IMesaBO {
    
    public abstract void insertarMesasMasivo() throws NegocioException;
    
    public abstract Mesa buscarPorId(Long id) throws NegocioException;
    
    public abstract List<Mesa> obtenerTodos() throws NegocioException;

    public abstract List<Mesa> obtenerMesasLibres() throws NegocioException;

    public abstract Mesa actualizarDisponibilidad(Long id) throws NegocioException;
    
}
