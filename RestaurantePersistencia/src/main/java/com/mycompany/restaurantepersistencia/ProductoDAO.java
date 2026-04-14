package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.EstadoProducto;
import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedominio.Producto;
import com.mycompany.restaurantedominio.ProductoIngrediente;
import com.mycompany.restaurantedominio.ManejadorConexiones;
import com.mycompany.restaurantedtos.ProductoDTO;
import com.mycompany.restaurantedtos.ProductoIngredienteDTO;
import com.mycompany.restaurantepersistencia.adapters.TipoProductoDominioATipoProductoDTOAdapter;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.TypedQuery;


/**
 * Implementación de {@link IProductoDAO} utilizando JPA.
 * 
 * Esta clase se encarga de la persistencia de la entidad {@link Producto},
 * incluyendo su relación con {@link ProductoIngrediente}.
 * 
 * Funcionalidades:
 * - Registrar productos junto con sus ingredientes
 * - Actualizar productos y sus relaciones
 * - Cambiar estado de productos
 * - Consultar productos
 * - Validar existencia de nombres activos
 * 
 */
public class ProductoDAO implements IProductoDAO {

    private static final Logger LOGGER = Logger.getLogger(ProductoDAO.class.getName());

    /**
     * Registra un nuevo producto junto con sus ingredientes.
     * 
     * Flujo:
     * 1. Se crea el producto base
     * 2. Se persiste el producto
     * 3. Se asocian y persisten los ingredientes
     * 
     * @param dto datos del producto a registrar
     * @return producto persistido
     * @throws PersistenciaException si ocurre un error en la persistencia
     */
    @Override
    public Producto registrar(ProductoDTO dto) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();

            Producto producto = new Producto(
                dto.getNombre(),
                dto.getDescripcion(),
                dto.getPrecio(),
                TipoProductoDominioATipoProductoDTOAdapter.adaptar(dto.getTipo())
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


    /**
     * Actualiza un producto existente y reemplaza completamente
     * sus ingredientes asociados.
     * 
     * Flujo:
     * 1. Se obtiene el producto
     * 2. Se actualizan sus atributos
     * 3. Se eliminan relaciones actuales de ingredientes
     * 4. Se agregan nuevas relaciones
     * 
     * @param dto datos actualizados del producto
     * @return producto actualizado
     * @throws PersistenciaException si ocurre un error
     */
    @Override
    public Producto actualizar(ProductoDTO dto) throws PersistenciaException {
        try {
            EntityManager em = ManejadorConexiones.crearEntityManager();
            em.getTransaction().begin();

            Producto producto = em.find(Producto.class, dto.getIdProducto());
            producto.setNombre(dto.getNombre());
            producto.setDescripcion(dto.getDescripcion());
            producto.setPrecio(dto.getPrecio());
            producto.setTipo(TipoProductoDominioATipoProductoDTOAdapter.adaptar(dto.getTipo()));

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

    /**
     * Cambia el estado de un producto.
     * 
     * @param id identificador del producto
     * @param estado nuevo estado
     * @return producto actualizado
     * @throws PersistenciaException si ocurre un error
     */
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

    /**
     * Busca un producto por su ID.
     * 
     * @param id identificador del producto
     * @return producto encontrado o null si no existe
     * @throws PersistenciaException si ocurre un error
     */
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

    /**
     * Obtiene todos los productos registrados.
     * 
     * @return lista de productos (puede estar vacía, nunca null)
     * @throws PersistenciaException si ocurre un error
     */
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

    /**
     * Verifica si existe un producto activo con el mismo nombre.
     * 
     * @param nombre nombre del producto
     * @return true si existe un producto activo con ese nombre
     * @throws PersistenciaException si ocurre un error
     */
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