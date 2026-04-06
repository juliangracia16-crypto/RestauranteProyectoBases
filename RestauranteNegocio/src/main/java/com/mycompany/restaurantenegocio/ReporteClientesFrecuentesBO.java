/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.ClienteFrecuente;
import com.mycompany.restaurantedtos.ReporteClientesFrecuentesDTO;
import com.mycompany.restaurantepersistencia.ClienteDAO;
import com.mycompany.restaurantepersistencia.IClienteDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ReporteClientesFrecuentesBO implements IReporteClientesFrecuentesBO {
    
    private final IClienteDAO clienteDAO;

    public ReporteClientesFrecuentesBO(IClienteDAO clienteDAO) {
        this.clienteDAO = clienteDAO;
    }

    @Override
    public List<ReporteClientesFrecuentesDTO> obtenerReporte() throws NegocioException {
        try {
            List<ClienteFrecuente> clientes = clienteDAO.obtenerTodos();
            List<ReporteClientesFrecuentesDTO> reporte = new ArrayList<>();
            for (ClienteFrecuente cf : clientes) {
                reporte.add(new ReporteClientesFrecuentesDTO(
                    cf.getNombre(),
                    cf.getNumVisitas(),
                    cf.getTotalGastado(),
                    cf.getFechaUltimaVisita()
                ));
            }
            return reporte;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al obtener el reporte de clientes frecuentes.", ex);
        }
    }

    @Override
    public List<ReporteClientesFrecuentesDTO> filtrarPorNombre(String nombre) throws NegocioException {
        if (nombre == null || nombre.isBlank()) {
            throw new NegocioException("El nombre no puede estar vacío.");
        }
        try {
            List<ClienteFrecuente> clientes = clienteDAO.obtenerTodos();
            List<ReporteClientesFrecuentesDTO> reporte = new ArrayList<>();
            for (ClienteFrecuente cf : clientes) {
                if (cf.getNombre().toLowerCase().contains(nombre.toLowerCase())) {
                    reporte.add(new ReporteClientesFrecuentesDTO(
                        cf.getNombre(),
                        cf.getNumVisitas(),
                        cf.getTotalGastado(),
                        cf.getFechaUltimaVisita()
                    ));
                }
            }
            return reporte;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al filtrar por nombre.", ex);
        }
    }

    @Override
    public List<ReporteClientesFrecuentesDTO> filtrarPorMinimoVisitas(int minimoVisitas) throws NegocioException {
        if (minimoVisitas < 0) {
            throw new NegocioException("El número mínimo de visitas no puede ser negativo.");
        }
        try {
            List<ClienteFrecuente> clientes = clienteDAO.obtenerTodos();
            List<ReporteClientesFrecuentesDTO> reporte = new ArrayList<>();
            for (ClienteFrecuente cf : clientes) {
                if (cf.getNumVisitas() >= minimoVisitas) {
                    reporte.add(new ReporteClientesFrecuentesDTO(
                        cf.getNombre(),
                        cf.getNumVisitas(),
                        cf.getTotalGastado(),
                        cf.getFechaUltimaVisita()
                    ));
                }
            }
            return reporte;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al filtrar por mínimo de visitas.", ex);
        }
    }

    
       
    
    
}
