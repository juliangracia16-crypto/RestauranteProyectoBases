package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedtos.ProductoDTO;
import java.util.List;


/**
 * Interfaz que define las operaciones de acceso a datos (DAO)
 * para la entidad
 * 
 */
public interface IProductoDAO {

    
    /**
     * Registra un nuevo producto en la base de datos.
     * 
     * @param dto objeto de transferencia con los datos del producto
     * @return el producto registrado con sus datos persistidos
     * @throws PersistenciaException si ocurre un error en la persistencia
     */
    public abstract Producto registrar(ProductoDTO dto) throws PersistenciaException;

    /**
     * Actualiza un producto existente en la base de datos.
     * 
     * @param dto objeto de transferencia con los datos actualizados
     * @return el producto actualizado
     * @throws PersistenciaException si ocurre un error en la persistencia
     */
    public abstract Producto actualizar(ProductoDTO dto) throws PersistenciaException;

    /**
     * Cambia el estado de un producto (por ejemplo: activo/inactivo).
     * 
     * @param id identificador del producto
     * @param estado nuevo estado del producto
     * @return el producto con el estado actualizado
     * @throws PersistenciaException si ocurre un error en la persistencia
     */
    public abstract Producto cambiarEstado(Long id, EstadoProducto estado) throws PersistenciaException;

    /**
     * Busca un producto por su identificador.
     * 
     * @param id identificador del producto
     * @return el producto encontrado
     * @throws PersistenciaException si ocurre un error en la persistencia
     */
    public abstract Producto buscarPorId(Long id) throws PersistenciaException;

    /**
     * Obtiene todos los productos registrados en el sistema.
     * 
     * @return lista de productos
     * @throws PersistenciaException si ocurre un error en la persistencia
     */
    public abstract List<Producto> obtenerTodos() throws PersistenciaException;

    /**
     * Verifica si existe un producto activo con el mismo nombre.
     * 
     * 
     * @param nombre nombre del producto a validar
     * @return true si existe un producto activo con ese nombre, false en caso contrario
     * @throws PersistenciaException si ocurre un error en la persistencia
     */
    public abstract boolean existeNombreActivo(String nombre) throws PersistenciaException;
}