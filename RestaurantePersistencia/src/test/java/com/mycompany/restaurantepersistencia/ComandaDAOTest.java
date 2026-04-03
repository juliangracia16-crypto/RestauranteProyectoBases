/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Comanda;
import com.mycompany.restaurantedominio.EstadoComanda;
import com.mycompany.restaurantedominio.Mesa;
import com.mycompany.restaurantedtos.ClienteFrecuenteDTO;
import com.mycompany.restaurantedtos.ComandaDTO;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ComandaDAOTest {
    
//    private ComandaDAO dao;
//    private MesaDAO mesaDAO;
//    private ClienteDAO clienteDAO;
//
//    @BeforeEach
//    public void setUp() {
//        dao = new ComandaDAO();
//        mesaDAO = new MesaDAO();
//        clienteDAO = new ClienteDAO();
//    }
//    
//    @Test
//    public void testCrearComandaConClienteGeneralFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa    = mesaDAO.obtenerTodos().get(0).getId();
//            Long idCliente = clienteDAO.crear().getIdCliente();
//            ComandaDTO dto = new ComandaDTO(idMesa, idCliente);
//            // ejecución + verificación
//            Comanda resultado = dao.crear(dto);
//            assertNotNull(resultado.getId());
//            assertNotNull(resultado.getFolio());
//            assertNotNull(resultado.getMesa());
//            assertNotNull(resultado.getCliente());
//            assertTrue(resultado.getFolio().startsWith("OB-"));
//            assertEquals(EstadoComanda.ABIERTA, resultado.getEstado());
//            assertEquals(0.0, resultado.getTotal());
//        });
//    }
//    
//    @Test
//    public void testCrearComandaConClienteFrecuenteFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa = mesaDAO.obtenerTodos().get(0).getId();
//
//            // Creamos un cliente frecuente unico 
//            ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Testeo", "644" + System.currentTimeMillis() % 9999999L, null);
//            Long idCliente = clienteDAO.crear(clienteDTO).getIdCliente();
//
//            ComandaDTO dto = new ComandaDTO(idMesa, idCliente);
//            //ejecución + verificación
//            Comanda resultado = dao.crear(dto);
//            assertNotNull(resultado.getId());
//            assertNotNull(resultado.getFolio());
//            assertNotNull(resultado.getMesa());
//            assertNotNull(resultado.getCliente());
//            assertTrue(resultado.getFolio().startsWith("OB-"));
//            assertEquals(EstadoComanda.ABIERTA, resultado.getEstado());
//            assertEquals(0.0, resultado.getTotal());
//        });
//    }
//
//    @Test
//    public void testCrearComandaFolioUnicoFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            List<Mesa> mesas = mesaDAO.obtenerTodos();
//            Long idCliente   = clienteDAO.crear().getIdCliente();
//            //ejecución + verificación
//            Comanda comanda1 = dao.crear(new ComandaDTO(mesas.get(0).getId(), idCliente));
//            Comanda comanda2 = dao.crear(new ComandaDTO(mesas.get(1).getId(), idCliente));
//            assertNotNull(comanda1.getFolio());
//            assertNotNull(comanda2.getFolio());
//            assertNotEquals(comanda1.getFolio(), comanda2.getFolio());
//        });
//    }
//
//    @Test
//    public void testActualizarComandaFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa    = mesaDAO.obtenerTodos().get(0).getId();
//            Long idCliente = clienteDAO.crear().getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            ComandaDTO dtoActualizar = new ComandaDTO(150.0);
//            Comanda resultado = dao.actualizar(creada.getId(), dtoActualizar);
//            assertNotNull(resultado);
//            assertEquals(150.0, resultado.getTotal());
//        });
//    }
//
//    @Test
//    public void testBuscarPorIdFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa    = mesaDAO.obtenerTodos().get(0).getId();
//            Long idCliente = clienteDAO.crear().getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            Comanda resultado = dao.buscarPorId(creada.getId());
//            assertNotNull(resultado);
//            assertEquals(creada.getId(), resultado.getId());
//            assertEquals(creada.getFolio(), resultado.getFolio());
//        });
//    }
//
//    @Test
//    public void testBuscarPorIdInexistenteRetornaNull() {
//        //set up
//        Long id = 99999L;
//        //ejecución + verificación
//        assertDoesNotThrow(() -> {
//            Comanda resultado = dao.buscarPorId(id);
//            assertNull(resultado);
//        });
//    }
//
//    @Test
//    public void testBuscarPorFolioFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa    = mesaDAO.obtenerTodos().get(0).getId();
//            Long idCliente = clienteDAO.crear().getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            Comanda resultado = dao.buscarPorFolio(creada.getFolio());
//            assertNotNull(resultado);
//            assertEquals(creada.getFolio(), resultado.getFolio());
//            assertEquals(creada.getId(), resultado.getId());
//        });
//    }
//
//    @Test
//    public void testBuscarPorFolioInexistenteFalla() {
//        String folio = "OB-99999999-999";
//        //ejecución + verificación
//        assertThrows(PersistenciaException.class, () -> {
//            dao.buscarPorFolio(folio);
//        });
//    }
//
//    @Test
//    public void testObtenerTodosFuncionaOk() {
//        //ejecución + verificación
//        assertDoesNotThrow(() -> {
//            List<Comanda> comandas = dao.obtenerTodos();
//            assertNotNull(comandas);
//        });
//    }
//
//    @Test
//    public void testBuscarPorRangoFechasFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa    = mesaDAO.obtenerTodos().get(0).getId();
//            Long idCliente = clienteDAO.crear().getIdCliente();
//            dao.crear(new ComandaDTO(idMesa, idCliente));
//            LocalDateTime inicio = LocalDateTime.now().minusDays(1);
//            LocalDateTime fin    = LocalDateTime.now().plusDays(1);
//            //ejecución + verificación
//            List<Comanda> resultado = dao.buscarPorRangoFechas(inicio, fin);
//            assertNotNull(resultado);
//            assertFalse(resultado.isEmpty());
//        });
//    }
//
//    @Test
//    public void testBuscarPorRangoFechasSinResultados() {
//        LocalDateTime inicio = LocalDateTime.now().minusDays(10);
//        LocalDateTime fin    = LocalDateTime.now().minusDays(9);
//        //ejecución + verificación
//        assertDoesNotThrow(() -> {
//            List<Comanda> resultado = dao.buscarPorRangoFechas(inicio, fin);
//            assertNotNull(resultado);
//            assertTrue(resultado.isEmpty());
//        });
//    }
//
//    @Test
//    public void testCancelarComandaFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa    = mesaDAO.obtenerTodos().get(0).getId();
//            Long idCliente = clienteDAO.crear().getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            Comanda resultado = dao.cancelar(creada.getId());
//            assertNotNull(resultado);
//            assertEquals(EstadoComanda.CANCELADA, resultado.getEstado());
//        });
//    }
//
//
//    @Test
//    public void testEntregarComandaFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa    = mesaDAO.obtenerTodos().get(0).getId();
//            Long idCliente = clienteDAO.crear().getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            Comanda resultado = dao.entregar(creada.getId());
//            assertNotNull(resultado);
//            assertEquals(EstadoComanda.ENTREGADA, resultado.getEstado());
//        });
//    }
//    
//    @Test
//    public void testCancelarComandaConClienteFrecuenteFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa = mesaDAO.obtenerTodos().get(0).getId();
//            ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Testeo", "644" + System.currentTimeMillis() % 9999999L, null);
//            Long idCliente = clienteDAO.crear(clienteDTO).getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            Comanda resultado = dao.cancelar(creada.getId());
//            assertNotNull(resultado);
//            assertEquals(EstadoComanda.CANCELADA, resultado.getEstado());
//        });
//    }
//
//    @Test
//    public void testEntregarComandaConClienteFrecuenteFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa = mesaDAO.obtenerTodos().get(0).getId();
//            ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Testeo", "644" + System.currentTimeMillis() % 9999999L, null);
//            Long idCliente = clienteDAO.crear(clienteDTO).getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            Comanda resultado = dao.entregar(creada.getId());
//            assertNotNull(resultado);
//            assertEquals(EstadoComanda.ENTREGADA, resultado.getEstado());
//        });
//    }
//
//    @Test
//    public void testBuscarPorIdConClienteFrecuenteFuncionaOk() {
//        assertDoesNotThrow(() -> {
//            mesaDAO.insertarMesasMasivo();
//            Long idMesa = mesaDAO.obtenerTodos().get(0).getId();
//            ClienteFrecuenteDTO clienteDTO = new ClienteFrecuenteDTO("Testeo", "644" + System.currentTimeMillis() % 9999999L, null);
//            Long idCliente = clienteDAO.crear(clienteDTO).getIdCliente();
//            Comanda creada = dao.crear(new ComandaDTO(idMesa, idCliente));
//            //ejecución + verificación
//            Comanda resultado = dao.buscarPorId(creada.getId());
//            assertNotNull(resultado);
//            assertEquals(creada.getId(), resultado.getId());
//            assertEquals(creada.getFolio(), resultado.getFolio());
//        });
//    }

    
}

    