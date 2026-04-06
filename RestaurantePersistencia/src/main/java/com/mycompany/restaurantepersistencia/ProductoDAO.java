package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedominio.ProductoIngrediente;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedtos.ProductoDTO;
import com.mycompany.restaurantedtos.ProductoIngredienteDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;

public class ProductoDAO implements IProductoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    @Override
    public Producto registrar(ProductoDTO dto) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();

            Producto producto = new Producto(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                dto.getTipo() // TODO error en el tipo
            );

            if (dto.getImagen() != null) {
                producto.setImagen(dto.getImagen());
            }

            em.persist(producto);

            for (ProductoIngredienteDTO piDTO : dto.getIngredientes()) {
                Ingrediente ingrediente = em.find(Ingrediente.class, piDTO.getIdIngrediente());
                ProductoIngrediente pi = new ProductoIngrediente(producto, ingrediente, piDTO.getCantidad());
                em.persist(pi);
            }

            em.getTransaction().commit();
            return producto;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo registrar el producto.", ex);
        }
    }

    @Override
    public Producto actualizar(ProductoDTO dto) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();

            Producto producto = em.find(Producto.class, dto.getIdProducto());
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setTipo(dto.getTipo()); // TODO no sé como hacer aqui esto pq el tipo no es el mismo tiren paro y digan si saben que pedo

            if (dto.getImagen() != null) {
                producto.setImagen(dto.getImagen());
            }

            TypedQuery<ProductoIngrediente> query = em.createQuery(
                "SELECT pi FROM ProductoIngrediente pi WHERE pi.producto.id = :id",
                ProductoIngrediente.class
            );
            query.setParameter("id", dto.getIdProducto());
            for (ProductoIngrediente pi : query.getResultList()) {
                em.remove(pi);
            }

            for (ProductoIngredienteDTO piDTO : dto.getIngredientes()) {
                Ingrediente ingrediente = em.find(Ingrediente.class, piDTO.getIdIngrediente());
                ProductoIngrediente pi = new ProductoIngrediente(producto, ingrediente, piDTO.getCantidad());
                em.persist(pi);
            }

            em.persist(producto);
            em.getTransaction().commit();
            return producto;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo actualizar el producto.", ex);
        }
    }

    @Override
    public Producto cambiarEstado(Long id, EstadoProducto estado) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            producto.setEstado(estado);
            em.persist(producto);
            em.getTransaction().commit();
            return producto;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo cambiar el estado del producto.", ex);
        }
    }

    @Override
    public Producto buscarPorId(Long id) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();
            Producto producto = em.find(Producto.class, id);
            em.getTransaction().commit();
            return producto;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudo encontrar el producto con id: " + id, ex);
        }
    }

    @Override
    public List<Producto> obtenerTodos() throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();
            List<Producto> productos = em.createQuery(
                "SELECT p FROM Producto p", Producto.class
            ).getResultList();
            em.getTransaction().commit();
            return productos;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("No se pudieron obtener los productos.", ex);
        }
    }

    @Override
    public boolean existeNombreActivo(String nombre) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            Long count = em.createQuery(
                "SELECT COUNT(p) FROM Producto p WHERE p.nombre = :nombre AND p.estado = :estado",
                Long.class
            )
            .setParameter("nombre", nombre)
            .setParameter("estado", EstadoProducto.ACTIVO)
            .getSingleResult();
            return count > 0;
        } catch (PersistenceException ex) {
            LOGGER.severe(ex.getMessage());
            throw new PersistenciaException("Error al verificar nombre duplicado.", ex);
        }
    }
}