/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantepersistencia.adapters;

import com.mycompany.restaurantedominio.TipoProducto;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class TipoProductoDominioATipoProductoDTOAdapter {
     public static TipoProducto adaptar(com.mycompany.restaurantedtos.TipoProducto tipo) {
        TipoProducto tipoProducto = TipoProducto.OTRO;
        if (null != tipo) {
            switch (tipo) {
                case PLATILLO -> tipoProducto = TipoProducto.PLATILLO;
                case BEBIDA   -> tipoProducto = TipoProducto.BEBIDA;
                case POSTRE   -> tipoProducto = TipoProducto.POSTRE;
                case ENTRADA  -> tipoProducto = TipoProducto.ENTRADA;
                case OTRO     -> tipoProducto = TipoProducto.OTRO;
            }
        }
        return tipoProducto;
    }
}
