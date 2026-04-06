package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedtos.ProductoDTO;
import com.mycompany.restaurantedtos.ProductoIngredienteDTO;
import com.mycompany.restaurantepersistencia.IProductoDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.List;

public class ProductoBO implements IProductoBO {

    private final IProductoDAO productoDAO;

    public ProductoBO(IProductoDAO productoDAO) {
        this.productoDAO = productoDAO;
    }

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

    @Override
    public List<Producto> obtenerTodos() throws NegocioException {
        try {
            return productoDAO.obtenerTodos();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener los productos.", ex);
        }
    }

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