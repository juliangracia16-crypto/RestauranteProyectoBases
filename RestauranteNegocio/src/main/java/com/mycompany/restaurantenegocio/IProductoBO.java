package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedtos.ProductoDTO;
import java.util.List;
/**
 * Interfaz que define las operaciones de lógica de negocio
 * para la gestión de {@link Producto}.
 * 
 * Esta capa es responsable de:
 * - Validar reglas de negocio antes de persistir datos
 * - Evitar inconsistencias (como duplicados o datos inválidos)
 * - Coordinar la comunicación con la capa de persistencia
 * 
 * Funcionalidades principales:
 * - Registrar productos
 * - Actualizar productos
 * - Cambiar el estado de productos
 * - Consultar productos por ID o en conjunto
 * 
 */
public interface IProductoBO {

    /**
     * Registra un nuevo producto.
     * 
     * Reglas esperadas:
     * - El nombre no debe ser nulo ni vacío
     * - El precio debe ser mayor a 0
     * - El producto debe tener al menos un ingrediente
     * - No debe existir otro producto activo con el mismo nombre
     * 
     * @param dto objeto con los datos del producto
     * @return producto registrado
     * @throws NegocioException si ocurre un error o se incumple alguna regla
     */
    public abstract Producto registrar(ProductoDTO dto) throws NegocioException;

    /**
     * Actualiza un producto existente.
     * 
     * Reglas esperadas:
     * - El ID del producto debe ser válido
     * - Se deben cumplir las mismas validaciones que en el registro
     * - No debe existir duplicidad de nombre en productos activos
     * 
     * @param dto objeto con los datos actualizados
     * @return producto actualizado
     * @throws NegocioException si ocurre un error o se incumple alguna regla
     */
    public abstract Producto actualizar(ProductoDTO dto) throws NegocioException;

    /**
     * Cambia el estado de un producto.
     * 
     * @param id identificador del producto
     * @param estado nuevo estado del producto
     * @return producto actualizado
     * @throws NegocioException si ocurre un error o los datos son inválidos
     */
    public abstract Producto cambiarEstado(Long id, EstadoProducto estado) throws NegocioException;

    /**
     * Busca un producto por su identificador.
     * 
     * @param id identificador del producto
     * @return producto encontrado (puede ser null si no existe)
     * @throws NegocioException si ocurre un error
     */
    public abstract Producto buscarPorId(Long id) throws NegocioException;

    /**
     * Obtiene todos los productos registrados en el sistema.
     * 
     * @return lista de productos
     * @throws NegocioException si ocurre un error
     */
    public abstract List<Producto> obtenerTodos() throws NegocioException;
}