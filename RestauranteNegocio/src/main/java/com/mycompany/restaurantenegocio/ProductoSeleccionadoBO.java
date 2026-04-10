/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedominio.ProductoSeleccionado;
import com.mycompany.restaurantepersistencia.IProductoSeleccionadoDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ProductoSeleccionadoBO implements IProductoSeleccionadoBO {
    
    private final IProductoSeleccionadoDAO productoSeleccionadoDAO;

    public ProductoSeleccionadoBO(IProductoSeleccionadoDAO productoSeleccionadoDAO) {
        this.productoSeleccionadoDAO = productoSeleccionadoDAO;
    }


    @Override
    public ProductoSeleccionado agregar(Comanda comanda, Producto producto, Integer cantidad, Double precioUnitario, String comentario) throws NegocioException {
        if (comanda == null) {
            throw new NegocioException("La comanda no puede ser nula.");
        }
        if (producto == null) {
            throw new NegocioException("El producto no puede ser nulo.");
        }
        if (cantidad == null || cantidad <= 0) {
            throw new NegocioException("La cantidad debe ser mayor a 0.");
        }
        if (precioUnitario == null || precioUnitario <= 0) {
            throw new NegocioException("El precio unitario debe ser mayor a 0.");
        }
        try {
            ProductoSeleccionado ps = new ProductoSeleccionado(comanda, producto, cantidad, precioUnitario, comentario);
            return productoSeleccionadoDAO.agregar(ps);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al agregar el producto a la comanda.", ex);
        }
    }

    @Override
    public void eliminar(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El id del producto seleccionado no puede ser nulo.");
        }
        try {
            productoSeleccionadoDAO.eliminar(id);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al eliminar el producto de la comanda.", ex);
        }
    }

    @Override
    public List<ProductoSeleccionado> obtenerPorComanda(Long idComanda) throws NegocioException {
        if (idComanda == null) {
            throw new NegocioException("El id de la comanda no puede ser nulo.");
        }
        try {
            return productoSeleccionadoDAO.obtenerPorComanda(idComanda);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener los productos de la comanda.", ex);
        }
    }
    
}
