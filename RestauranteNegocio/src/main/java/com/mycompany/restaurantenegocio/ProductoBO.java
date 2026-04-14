package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedtos.ProductoDTO;
import com.mycompany.restaurantedtos.ProductoIngredienteDTO;
import com.mycompany.restaurantepersistencia.IProductoDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.List;


/**
 * Implementación de la lógica de negocio para {@link Producto}.
 * 
 * Esta clase valida reglas de negocio antes de delegar operaciones
 * a la capa de persistencia ({@link IProductoDAO}).
 * 
 * Funcionalidades principales:
 * - Registrar productos
 * - Actualizar productos
 * - Cambiar estado de productos
 * - Consultar productos
 * 
 * Reglas de negocio aplicadas:
 * - El nombre es obligatorio y no debe exceder 100 caracteres
 * - El precio debe ser mayor a 0
 * - El tipo de producto es obligatorio
 * - Debe existir al menos un ingrediente
 * - Cada ingrediente debe tener cantidad mayor a 0
 * - No puede existir otro producto activo con el mismo nombre
 * 
 */
public class ProductoBO implements IProductoBO {

    private final IProductoDAO productoDAO;

    /**
     * Constructor con inyección de dependencia del DAO.
     * 
     * @param productoDAO implementación del DAO
     */
    public ProductoBO(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

    /**
     * Registra un nuevo producto.
     * 
     * Flujo:
     * 1. Validación de datos
     * 2. Verificación de duplicidad de nombre activo
     * 3. Delegación a persistencia
     * 
     * @param dto datos del producto
     * @return producto registrado
     * @throws NegocioException si ocurre un error o validación falla
     */
    @Override
    public Producto registrar(ProductoDTO dto) throws NegocioException {
        validarDTO(dto);
        try {
            if (productoDAO.existeNombreActivo(dto.getNombre())) {
                throw new NegocioException("Ya existe un producto activo con el nombre: " + dto.getNombre());
            }
            return productoDAO.registrar(dto);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al registrar el producto.", ex);
        }
    }

    /**
     * Actualiza un producto existente.
     * 
     * Flujo:
     * 1. Validación de ID
     * 2. Validación de datos
     * 3. Validación de duplicidad de nombre (si cambia)
     * 4. Delegación a persistencia
     * 
     * @param dto datos actualizados del producto
     * @return producto actualizado
     * @throws NegocioException si ocurre un error o validación falla
     */
    @Override
    public Producto actualizar(ProductoDTO dto) throws NegocioException {
        if (dto.getIdProducto() == null || dto.getIdProducto() <= 0) {
            throw new NegocioException("El ID del producto a actualizar no es válido.");
        }
        validarDTO(dto);
        try {
            Producto actual = productoDAO.buscarPorId(dto.getIdProducto());
            if (!actual.getNombre().equalsIgnoreCase(dto.getNombre())) {
                if (productoDAO.existeNombreActivo(dto.getNombre())) {
                    throw new NegocioException("Ya existe un producto activo con el nombre: " + dto.getNombre());
                }
            }
            return productoDAO.actualizar(dto);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al actualizar el producto.", ex);
        }
    }
    
    /**
     * Cambia el estado de un producto.
     * 
     * @param id identificador del producto
     * @param estado nuevo estado
     * @return producto actualizado
     * @throws NegocioException si ocurre un error o validación falla
     */
    @Override
    public Producto cambiarEstado(Long id, EstadoProducto estado) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del producto no puede ser nulo.");
        }
        if (estado == null) {
            throw new NegocioException("El estado no puede ser nulo.");
        }
        try {
            return productoDAO.cambiarEstado(id, estado);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al cambiar el estado del producto.", ex);
        }
    }

    /**
     * Busca un producto por su ID.
     * 
     * @param id identificador del producto
     * @return producto encontrado (puede ser null)
     * @throws NegocioException si ocurre un error
     */
    @Override
    public Producto buscarPorId(Long id) throws NegocioException {
        if (id == null) {
            throw new NegocioException("El ID del producto no puede ser nulo.");
        }
        try {
            return productoDAO.buscarPorId(id);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al buscar el producto con id: " + id, ex);
        }
    }

    /**
     * Obtiene todos los productos registrados.
     * 
     * @return lista de productos (puede estar vacía, nunca null)
     * @throws NegocioException si ocurre un error
     */
    @Override
    public List<Producto> obtenerTodos() throws NegocioException {
        try {
            return productoDAO.obtenerTodos();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener los productos.", ex);
        }
    }

    /**
     * Valida los datos del {@link ProductoDTO}.
     * 
     * @param dto objeto a validar
     * @throws NegocioException si alguna validación falla
     */
    private void validarDTO(ProductoDTO dto) throws NegocioException {
        if (dto.getNombre() == null || dto.getNombre().trim().isEmpty()) {
            throw new NegocioException("El nombre del producto es obligatorio.");
        }
        if (dto.getNombre().length() > 100) {
            throw new NegocioException("El nombre del producto no puede superar los 100 caracteres.");
        }
        if (dto.getPrecio() == null || dto.getPrecio() <= 0) {
            throw new NegocioException("El precio debe ser mayor a 0.");
        }
        if (dto.getTipo() == null) {
            throw new NegocioException("El tipo de producto es obligatorio.");
        }
        if (dto.getIngredientes() == null || dto.getIngredientes().isEmpty()) {
            throw new NegocioException("El producto debe tener al menos un ingrediente.");
        }
        for (ProductoIngredienteDTO pi : dto.getIngredientes()) {
            if (pi.getCantidad() == null || pi.getCantidad() <= 0) {
                throw new NegocioException("La cantidad de cada ingrediente debe ser mayor a 0.");
            }
        }
    }
}