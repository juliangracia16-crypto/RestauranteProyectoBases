
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedtos.ClienteFrecuenteDTO;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Julian
 */
public class ClienteDAOTest {
    private ClienteDAO clienteDAO;
    public ClienteDAOTest() {
    }
    
    @BeforeEach()
    public void init(){
        this.clienteDAO = new ClienteDAO();
    }
    
    @Test
    public void testCrearClienteFrecuenteFuncionaOk() {
        ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Julian Gracia","6331234591","julian@mail.com");
        assertDoesNotThrow(()->{
            ClienteFrecuente clienteRegistrado = clienteDAO.crear(clienteDTO);
            assertNotNull(clienteRegistrado.getIdCliente());
        });
    }
    @Test
    public void testCrearClienteFrecuenteLanzaExcepcionOk(){
        ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Julian Gracia",null,"julian@mail.com");
        String mensajeEsperado = "No se pudo guardar el cliente";
        Exception ex = assertThrows(PersistenciaException.class,()->{
            clienteDAO.crear(clienteDTO);
        });
        assertEquals(mensajeEsperado,ex.getMessage());
    }
    @Test
    public void testActualizarClienteFrecuenteFuncionaOk(){
        Long idPrueba = 1l; //cambiarlo si se desea probar que actualice otro cliente
        ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO(idPrueba,"Luis Garcia","6441234591","luis@mail.com");
        assertDoesNotThrow(()->{
            ClienteFrecuente clienteActualizado = clienteDAO.actualizar(clienteDTO);
            assertNotNull(clienteActualizado);
            assertEquals(clienteDTO.getNombre(),clienteActualizado.getNombre());
            assertEquals(clienteDTO.getTelefono(),clienteActualizado.getTelefono());
            assertEquals(clienteDTO.getCorreo(),clienteActualizado.getCorreo());
        });
    }
    
    
}
