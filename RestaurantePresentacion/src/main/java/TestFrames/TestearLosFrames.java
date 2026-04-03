/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package TestFrames;

import com.mycompany.restaurantenegocio.ComandaBO;
import com.mycompany.restaurantenegocio.IComandaBO;
import com.mycompany.restaurantenegocio.IMesaBO;
import com.mycompany.restaurantenegocio.MesaBO;
import com.mycompany.restaurantepersistencia.ComandaDAO;
import com.mycompany.restaurantepersistencia.IComandaDAO;
import com.mycompany.restaurantepersistencia.IMesaDAO;
import com.mycompany.restaurantepersistencia.MesaDAO;
import com.mycompany.restaurantepresentacion.FrmGestionComandas;


/**
 *
 * @author PC GAMER MASTER RACE
 */
public class TestearLosFrames {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        IComandaDAO comandaDAO = new ComandaDAO();
        IMesaDAO mesaDAO = new MesaDAO();
        IComandaBO comandaBO = new ComandaBO(comandaDAO);
        IMesaBO mesaBO = new MesaBO(mesaDAO);
        FrmGestionComandas frame = new FrmGestionComandas(comandaBO, mesaBO);
        frame.setVisible(true);
    }
    
}
