
package com.mycompany.restaurantepersistencia;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;
import com.mycompany.restaurantedtos.UnidadMedida;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;

/**
 *
 * @author Julian
 */
public class IngredientesDAOTest {
    private IIngredientesDAO ingredientesDAO;
    public IngredientesDAOTest() {
    }
    @BeforeEach()
    public void init(){
        this.ingredientesDAO = new IngredientesDAO();
    }
    @Test
    public void testRegistrarIngredienteFuncionaOk() {
        NuevoIngredienteDTO ingredienteDTO = new NuevoIngredienteDTO("Sal",950,UnidadMedida.GRAMOS);
        assertDoesNotThrow(()->{
            Ingrediente ingrediente = ingredientesDAO.registrarIngrediente(ingredienteDTO);
            assertNotNull(ingrediente.getId());
        });
    }
    
}
