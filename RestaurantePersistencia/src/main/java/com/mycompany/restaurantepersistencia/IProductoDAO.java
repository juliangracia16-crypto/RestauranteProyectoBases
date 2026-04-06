package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedtos.ProductoDTO;
import java.util.List;

public interface IProductoDAO {

    public abstract Producto registrar(ProductoDTO dto) throws PersistenciaException;

    public abstract Producto actualizar(ProductoDTO dto) throws PersistenciaException;

    public abstract Producto cambiarEstado(Long id, EstadoProducto estado) throws PersistenciaException;

    public abstract Producto buscarPorId(Long id) throws PersistenciaException;

    public abstract List<Producto> obtenerTodos() throws PersistenciaException;

    public abstract boolean existeNombreActivo(String nombre) throws PersistenciaException;
}