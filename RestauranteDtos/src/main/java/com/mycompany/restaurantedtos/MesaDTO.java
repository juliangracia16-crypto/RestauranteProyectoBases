/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedtos;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class MesaDTO {
    private Integer numeroMesa;
    private String disponibilidad;

    public MesaDTO() {}

    public MesaDTO(Integer numeroMesa, String disponibilidad) {
        this.numeroMesa = numeroMesa;
        this.disponibilidad = disponibilidad;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public String getDisponibilidad() {
        return disponibilidad;
    }
    
}
