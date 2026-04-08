
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedominio.ClienteGeneral;
import com.mycompany.restaurantedtos.ClienteFrecuenteDTO;
import com.mycompany.restaurantepersistencia.IClienteDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import com.mycompany.restauranteutilidades.EncriptadorAES;
import java.util.List;

/**
 *
 * @author Julian
 */
public class ClienteBO implements IClienteBO{
    private final IClienteDAO clienteDAO;

    public ClienteBO(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }
    
    @Override
    public ClienteFrecuente crear(ClienteFrecuenteDTO cliente) throws NegocioException {
        if(cliente.getTelefono() == null){
            throw new NegocioException("El telefono es obligatorio.");
        }
        if(cliente.getTelefono().length()>16){
            throw new NegocioException("Numero telefonico muy largo. Maximo 16 digitos");
        }
        if(cliente.getNombre() == null){
            throw new NegocioException("El nombre no puede ser nulo.");
        }
        if(cliente.getNombre().length() > 50){
            throw new NegocioException("El nombre es muy largo. Maximo 50 caracteres.");
        }
        if(!esCorreoValido(cliente.getCorreo())){
            throw new NegocioException("Correo electronico con formato invalido.");
        }
        try{
            cliente.setTelefono(EncriptadorAES.encriptar(cliente.getTelefono()));
            ClienteFrecuente clienteFrecuente = clienteDAO.crear(cliente);
            return clienteFrecuente;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al intentar registrar el cliente frecuente.",ex);
        }
    }

    @Override
    public ClienteFrecuente actualizar(ClienteFrecuenteDTO cliente) throws NegocioException {
        if(cliente.getTelefono() == null){
            throw new NegocioException("El telefono es obligatorio.");
        }
        if(cliente.getTelefono().length()>16){
            throw new NegocioException("Numero telefonico muy largo. Maximo 16 digitos");
        }
        if(cliente.getNombre() == null){
            throw new NegocioException("El nuevo nombre no puede ser nulo.");
        }
        if(cliente.getNombre().length() > 50){
            throw new NegocioException("El nuevo nombre es muy largo. Maximo 50 caracteres.");
        }
        if(!esCorreoValido(cliente.getCorreo())){
            throw new NegocioException("Nuevo orreo electronico con formato invalido.");
        }
        if(cliente.getIdCliente() <= 0){
            throw new NegocioException("El ID del cliente a actualizar debe ser un numero positivo.");
        }
        if(cliente.getIdCliente() == null){
            throw new NegocioException("El ID del cliente a actualizar no puede ser nulo.");
        }
        try{
            ClienteFrecuente clienteFrecuente = clienteDAO.actualizar(cliente);
            return clienteFrecuente;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al intentar actualizar datos del cliente frecuente.",ex);
        }
    }

    @Override
    public ClienteFrecuente eliminar(Long id) throws NegocioException {
        if(id == null){
            throw new NegocioException("El id del cliente a eliminar no puede ser nulo.");
        }
        try{
            ClienteFrecuente clienteFrecuente = clienteDAO.eliminar(id);
            return clienteFrecuente;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al intentar eliminar el cliente frecuente.",ex);
        }
    }

    @Override
    public ClienteFrecuente buscarPorId(Long id) throws NegocioException {
        if(id == null){
            throw new NegocioException("El id del cliente a buscar no puede ser nulo.");
        }
        try{
            ClienteFrecuente clienteFrecuente = clienteDAO.buscarPorId(id);
            clienteFrecuente.setTelefono(EncriptadorAES.desencriptar(clienteFrecuente.getTelefono()));
            return clienteFrecuente;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al intentar consultar el cliente frecuente: "+id,ex);
        }
    }

    @Override
    public List<ClienteFrecuente> obtenerTodos() throws NegocioException {
        try{
            List<ClienteFrecuente> clientesFrecuentes = clienteDAO.obtenerTodos();
            for(ClienteFrecuente cliente: clientesFrecuentes){
                cliente.setTelefono(EncriptadorAES.desencriptar(cliente.getTelefono()));
            }
            return clientesFrecuentes;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al intentar consultar todos los clientes frecuentes.",ex);
        }
    }

    @Override
    public ClienteGeneral crear() throws NegocioException {
        try{
            ClienteGeneral clienteGeneral = clienteDAO.crear();
            return clienteGeneral;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al intentar registrar el cliente general.",ex);
        }
    }
    
    private boolean esCorreoValido(String correo) {
        if(correo == null){
            return true;
        }
        String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        return correo.matches(regex);
    }
}
