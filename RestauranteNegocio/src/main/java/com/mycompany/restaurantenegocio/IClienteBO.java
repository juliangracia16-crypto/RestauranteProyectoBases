
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedominio.ClienteGeneral;
import com.mycompany.restaurantedtos.ClienteFrecuenteDTO;
import java.util.List;

/**
 *
 * @author Julian
 */
public interface IClienteBO {
    public abstract ClienteFrecuente crear(ClienteFrecuenteDTO cliente) throws NegocioException;
    public abstract ClienteFrecuente actualizar(ClienteFrecuenteDTO cliente) throws NegocioException;
    public abstract ClienteFrecuente eliminar(Long id) throws NegocioException;
    public abstract ClienteFrecuente buscarPorId(Long id) throws NegocioException;
    public abstract List<ClienteFrecuente> obtenerTodos() throws NegocioException;
    public abstract ClienteGeneral crear() throws NegocioException;
}
