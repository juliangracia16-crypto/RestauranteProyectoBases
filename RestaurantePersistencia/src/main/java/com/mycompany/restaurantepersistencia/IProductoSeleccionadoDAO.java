/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.ProductoSeleccionado;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IProductoSeleccionadoDAO {
    
    public abstract ProductoSeleccionado agregar(ProductoSeleccionado ps) throws PersistenciaException;
    
    public void eliminar(Long id) throws PersistenciaException;
    
    public abstract List<ProductoSeleccionado> obtenerPorComanda(Long idComanda) throws PersistenciaException;
    
}
