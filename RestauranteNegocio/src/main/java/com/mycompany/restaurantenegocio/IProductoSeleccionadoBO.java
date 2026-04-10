/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedominio.ProductoSeleccionado;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IProductoSeleccionadoBO {
    public ProductoSeleccionado agregar(Comanda comanda, Producto producto, Integer cantidad, Double precioUnitario, String comentario) throws NegocioException;
    
    public void eliminar(Long id) throws NegocioException;
    
    public List<ProductoSeleccionado> obtenerPorComanda(Long idComanda) throws NegocioException;
    
}
