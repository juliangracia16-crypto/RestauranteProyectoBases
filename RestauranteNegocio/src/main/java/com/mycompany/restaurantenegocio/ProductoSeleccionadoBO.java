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
 * Implementación de la lógica de negocio para {@link ProductoSeleccionado}.
 * 
 * Esta clase actúa como intermediaria entre la capa de presentación
 * y la capa de persistencia, validando reglas de negocio antes de
 * delegar operaciones al DAO.
 * 
 * Funcionalidades:
 * - Agregar productos a una comanda
 * - Eliminar productos de una comanda
 * - Consultar productos asociados a una comanda
 * 
 * Reglas de negocio aplicadas:
 * - La comanda no puede ser nula
 * - El producto no puede ser nulo
 * - La cantidad debe ser mayor a 0
 * - El precio unitario debe ser mayor a 0
 * 
 */
public class ProductoSeleccionadoBO implements IProductoSeleccionadoBO {
    
    private final IProductoSeleccionadoDAO productoSeleccionadoDAO;

    public ProductoSeleccionadoBO(IProductoSeleccionadoDAO productoSeleccionadoDAO) {
        this.productoSeleccionadoDAO = productoSeleccionadoDAO;
    }

    
    /**
     * Agrega un producto a una comanda.
     * 
     * Valida:
     * - Comanda válida
     * - Producto válido
     * - Cantidad mayor a 0
     * - Precio unitario mayor a 0
     * 
     * @param comanda comanda a la que se agregará el producto
     * @param producto producto a agregar
     * @param cantidad cantidad del producto
     * @param precioUnitario precio unitario del producto
     * @param comentario comentario opcional
     * @return el producto seleccionado registrado
     * @throws NegocioException si alguna validación falla o hay error en persistencia
     */
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

    /**
     * Elimina un producto seleccionado por su ID.
     * 
     * @param id identificador del producto seleccionado
     * @throws NegocioException si el ID es nulo o ocurre un error
     */
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

    /**
     * Obtiene todos los productos asociados a una comanda.
     * 
     * @param idComanda identificador de la comanda
     * @return lista de productos seleccionados
     * @throws NegocioException si el ID es nulo o ocurre un error
     */
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
