
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantepersistencia.ClienteDAO;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Julian
 */
public class ClienteBOTest {
    
    public ClienteBOTest() {
    }

    @Test
    public void testDesencriptarTelefonoAlConsultarClienteFuncionaOk() {
        IClienteBO clienteBO = new ClienteBO(new ClienteDAO());
        String telefonoEsperado = "6441234567";
        Long idPrueba = 6l;
        assertDoesNotThrow(()->{
            ClienteFrecuente cliente = clienteBO.buscarPorId(idPrueba);
            assertEquals(telefonoEsperado,cliente.getTelefono());
        });
    }
    
}
