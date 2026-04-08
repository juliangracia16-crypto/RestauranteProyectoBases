/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TestFrames;


import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantenegocio.ClienteBO;
import com.mycompany.restaurantenegocio.ComandaBO;
import com.mycompany.restaurantenegocio.IClienteBO;
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
import com.mycompany.restaurantepresentacion.FrmGeneracionReportes;
import com.mycompany.restaurantepresentacion.FrmGestionComandas;
import com.mycompany.restaurantepresentacion.FrmGestionDeComandas;
import com.mycompany.restaurantepresentacion.FrmInicio;
import com.mycompany.restaurantepresentacion.FrmListaClientes;
import com.mycompany.restaurantepresentacion.FrmRegistrarNuevoIngrediente;
import com.mycompany.restaurantepresentacion.FrmRegistroCliente;
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
        IProductoDAO productoDAO = new ProductoDAO();

        IClienteBO clienteBO = new ClienteBO(clienteDAO);
        IComandaBO comandaBO = new ComandaBO(comandaDAO);
        IMesaBO mesaBO = new MesaBO(mesaDAO);
        IProductoBO productoBO = new ProductoBO(productoDAO);
        IReporteClientesFrecuentesBO reporteBO = new ReporteClientesFrecuentesBO(clienteDAO);
        
        //FrmInicio frame = new FrmInicio(clienteBO, comandaBO, mesaBO, productoBO, reporteBO);
        //FrmRegistroCliente frame = new FrmRegistroCliente(null,true,(ClienteBO)clienteBO);
        FrmListaClientes frame = new FrmListaClientes(null,true);
        frame.setVisible(true);
        
    }
    
}
