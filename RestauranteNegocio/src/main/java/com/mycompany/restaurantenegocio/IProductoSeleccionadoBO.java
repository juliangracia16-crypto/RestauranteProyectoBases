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
 * Interfaz que define las operaciones de lógica de negocio
 * para la gestión de {@link ProductoSeleccionado}.
 * 
 * Esta capa se encarga de:
 * - Validar reglas de negocio antes de persistir datos
 * - Coordinar operaciones entre presentación y persistencia
 * 
 * Funcionalidades principales:
 * - Agregar productos a una comanda
 * - Eliminar productos de una comanda
 * - Consultar productos asociados a una comanda
 * 
 * Las implementaciones deben garantizar:
 * - Validación de datos de entrada
 * - Manejo de excepciones mediante {@link NegocioException}
 * - Consistencia de las reglas de negocio
 * 
 */
public interface IProductoSeleccionadoBO {
    
    /**
     * Agrega un producto a una comanda.
     * 
     * Reglas esperadas:
     * - La comanda no debe ser nula
     * - El producto no debe ser nulo
     * - La cantidad debe ser mayor a 0
     * - El precio unitario debe ser mayor a 0
     * 
     * @param comanda comanda a la que se agregará el producto
     * @param producto producto a agregar
     * @param cantidad cantidad del producto
     * @param precioUnitario precio unitario del producto
     * @param comentario comentario opcional
     * @return el {@link ProductoSeleccionado} creado
     * @throws NegocioException si ocurre un error o se incumple alguna regla
     */
    public ProductoSeleccionado agregar(Comanda comanda, Producto producto, Integer cantidad, Double precioUnitario, String comentario) throws NegocioException;
    
    /**
     * Elimina un producto seleccionado por su ID.
     * 
     * @param id identificador del producto seleccionado
     * @throws NegocioException si el ID es inválido o ocurre un error
     */
    public void eliminar(Long id) throws NegocioException;
    
    /**
     * Obtiene los productos seleccionados asociados a una comanda.
     * 
     * @param idComanda identificador de la comanda
     * @return lista de {@link ProductoSeleccionado} (puede estar vacía, nunca null)
     * @throws NegocioException si el ID es inválido o ocurre un error
     */
    public List<ProductoSeleccionado> obtenerPorComanda(Long idComanda) throws NegocioException;
    
}
