package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedtos.ProductoDTO;
import java.util.List;

public interface IProductoBO {

    public abstract Producto registrar(ProductoDTO dto) throws NegocioException;

    public abstract Producto actualizar(ProductoDTO dto) throws NegocioException;

    public abstract Producto cambiarEstado(Long id, EstadoProducto estado) throws NegocioException;

    public abstract Producto buscarPorId(Long id) throws NegocioException;

    public abstract List<Producto> obtenerTodos() throws NegocioException;
}