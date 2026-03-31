/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.DisponibilidadMesa;
import com.mycompany.restaurantedominio.Mesa;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class MesaDAOTest {
    
    private MesaDAO dao;

    @BeforeEach
    public void setUp() {
        dao = new MesaDAO();
    }
    
    @Test
    public void testInsertarMesasMasivoFuncionaOk() {
        assertDoesNotThrow(() -> {
            dao.insertarMesasMasivo();
            List<Mesa> mesas = dao.obtenerTodos();
            assertNotNull(mesas);
            assertTrue(mesas.size() >= 20);
        });
    }



    @Test
    public void testObtenerTodosFuncionaOk() {
        assertDoesNotThrow(() -> {
            List<Mesa> mesas = dao.obtenerTodos();
            assertNotNull(mesas);
        });
    }

    @Test
    public void testObtenerMesasLibresFuncionaOk() {
        assertDoesNotThrow(() -> {
            dao.insertarMesasMasivo();
            List<Mesa> mesas = dao.obtenerMesasLibres();
            assertNotNull(mesas);
            // Todas deben ser LIBRE
            for (Mesa m : mesas) {
                assertEquals(DisponibilidadMesa.LIBRE, m.getDisponibilidad());
            }
        });
    }

    @Test
    public void testBuscarPorIdFuncionaOk() {
        assertDoesNotThrow(() -> {
            dao.insertarMesasMasivo();
            List<Mesa> mesas = dao.obtenerTodos();
            Long id = mesas.get(0).getId();

            Mesa resultado = dao.buscarPorId(id);
            assertNotNull(resultado);
            assertEquals(id, resultado.getId());
        });
    }
    
    @Test
    public void testActualizarDisponibilidadOcupadaALibreFuncionaOk() {
        assertDoesNotThrow(() -> {
            dao.insertarMesasMasivo();
            Long id = dao.obtenerTodos().get(0).getId();
            // Primera vez LIBRE -> OCUPADA
            dao.actualizarDisponibilidad(id);
            // Segunda vez OCUPADA -> LIBRE
            Mesa resultado = dao.actualizarDisponibilidad(id);
            assertNotNull(resultado);
            assertEquals(DisponibilidadMesa.LIBRE, resultado.getDisponibilidad());
        });
    }

    @Test
    public void testActualizarDisponibilidadLibreAOcupadaFuncionaOk() {
        // set up
        assertDoesNotThrow(() -> {
            dao.insertarMesasMasivo();
            Long id = dao.obtenerTodos().get(0).getId();
            // ejecución + verificación - empieza LIBRE debe quedar OCUPADA
            Mesa resultado = dao.actualizarDisponibilidad(id);
            assertNotNull(resultado);
            assertEquals(DisponibilidadMesa.OCUPADA, resultado.getDisponibilidad());
        });
    }

    
}
