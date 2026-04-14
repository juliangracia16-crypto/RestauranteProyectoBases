/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.ProductoSeleccionado;
import java.util.List;

/**
 * Interfaz DAO para la gestión de ProductoSeleccionado.
 * 
 * Define las operaciones de persistencia relacionadas con los productos
 * seleccionados dentro de una comanda (orden).
 * 
 * Funcionalidades principales:
 * - Agregar productos seleccionados a una comanda
 * - Eliminar productos seleccionados
 * - Consultar productos seleccionados por comanda
 * 
 */
public interface IProductoSeleccionadoDAO {
    
    /**
     * Agrega un producto seleccionado a la base de datos.
     * 
     * @param ps objeto {@link ProductoSeleccionado} a persistir
     * @return el producto seleccionado registrado
     * @throws PersistenciaException si ocurre un error durante la operación
     */
    public abstract ProductoSeleccionado agregar(ProductoSeleccionado ps) throws PersistenciaException;
    
    /**
     * Elimina un producto seleccionado por su identificador.
     * 
     * @param id identificador del producto seleccionado
     * @throws PersistenciaException si ocurre un error durante la eliminación
     */
    public void eliminar(Long id) throws PersistenciaException;
    
    /**
     * Obtiene todos los productos seleccionados asociados a una comanda.
     * 
     * @param idComanda identificador de la comanda
     * @return lista de {@link ProductoSeleccionado} asociados a la comanda
     * @throws PersistenciaException si ocurre un error durante la consulta
     */
    public abstract List<ProductoSeleccionado> obtenerPorComanda(Long idComanda) throws PersistenciaException;
    
}
