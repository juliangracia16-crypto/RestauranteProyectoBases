/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantepersistencia.adapters;

import com.mycompany.restaurantedominio.EstadoProducto;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class EstadoProductoDTOADominioAdapter {
    public static EstadoProducto adaptar(com.mycompany.restaurantedtos.EstadoProducto estado) {
        EstadoProducto estadoProducto = EstadoProducto.ACTIVO;
        if (null != estado) {
            switch (estado) {
                case ACTIVO   -> estadoProducto = EstadoProducto.ACTIVO;
                case INACTIVO -> estadoProducto = EstadoProducto.INACTIVO;
            }
        }
        return estadoProducto;
    }
    
}
