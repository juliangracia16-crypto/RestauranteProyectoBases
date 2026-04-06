/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedtos.ReporteClientesFrecuentesDTO;
import java.util.List;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public interface IReporteClientesFrecuentesBO {
    
    public abstract List<ReporteClientesFrecuentesDTO> obtenerReporte() throws NegocioException;

    public abstract List<ReporteClientesFrecuentesDTO> filtrarPorNombre(String nombre) throws NegocioException;

    public abstract List<ReporteClientesFrecuentesDTO> filtrarPorMinimoVisitas(int minimoVisitas) throws NegocioException;
    
}
