/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TestFrames;


import com.mycompany.restaurantenegocio.ComandaBO;
import com.mycompany.restaurantenegocio.IComandaBO;
import com.mycompany.restaurantenegocio.IIngredientesBO;
import com.mycompany.restaurantenegocio.IMesaBO;
import com.mycompany.restaurantenegocio.IProductoBO;
import com.mycompany.restaurantenegocio.IReporteClientesFrecuentesBO;
import com.mycompany.restaurantenegocio.IngredientesBO;
import com.mycompany.restaurantenegocio.MesaBO;
import com.mycompany.restaurantenegocio.ProductoBO;
import com.mycompany.restaurantenegocio.ReporteClientesFrecuentesBO;
import com.mycompany.restaurantepersistencia.ClienteDAO;
import com.mycompany.restaurantepersistencia.ComandaDAO;
import com.mycompany.restaurantepersistencia.IClienteDAO;
import com.mycompany.restaurantepersistencia.IComandaDAO;
import com.mycompany.restaurantepersistencia.IIngredientesDAO;
import com.mycompany.restaurantepersistencia.IMesaDAO;
import com.mycompany.restaurantepersistencia.IProductoDAO;
import com.mycompany.restaurantepersistencia.IngredientesDAO;
import com.mycompany.restaurantepersistencia.MesaDAO;
import com.mycompany.restaurantepersistencia.ProductoDAO;
import com.mycompany.restaurantepresentacion.FrmGestionComandas;
import com.mycompany.restaurantepresentacion.FrmRegistrarNuevoIngrediente;
import com.mycompany.restaurantepresentacion.FrmReporteClientesFrecuentes;


/**
 *
 * @author PC GAMER MASTER RACE
 */
public class TestearLosFrames {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IClienteDAO clienteDAO = new ClienteDAO();
        IComandaDAO comandaDAO = new ComandaDAO();
        IMesaDAO mesaDAO = new MesaDAO();
        IComandaBO comandaBO = new ComandaBO(comandaDAO);
        IMesaBO mesaBO = new MesaBO(mesaDAO);
        IProductoDAO productoDAO = new ProductoDAO();
        IProductoBO productoBO   = new ProductoBO(productoDAO);
        IIngredientesDAO ingredientesDAO = new IngredientesDAO();
        IIngredientesBO ingredientesBO = new IngredientesBO(ingredientesDAO);
        IReporteClientesFrecuentesBO reporteBO = new ReporteClientesFrecuentesBO(clienteDAO);
//        FrmGestionComandas frame = new FrmGestionComandas(comandaBO, mesaBO, productoBO);
//        FrmReporteClientesFrecuentes frame = new FrmReporteClientesFrecuentes(reporteBO);
        FrmRegistrarNuevoIngrediente frame = new FrmRegistrarNuevoIngrediente(ingredientesBO);
        frame.setVisible(true);
        
    }
    
}
