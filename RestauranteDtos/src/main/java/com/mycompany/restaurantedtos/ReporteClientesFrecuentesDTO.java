/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedtos;

import java.time.LocalDate;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ReporteClientesFrecuentesDTO {
    
    private String nombre;
    private int numVisitas;
    private double totalGastado;
    private LocalDate fechaUltimaVisita;

    public ReporteClientesFrecuentesDTO(String nombre, int numVisitas, double totalGastado, LocalDate fechaUltimaVisita) {
        this.nombre = nombre;
        this.numVisitas = numVisitas;
        this.totalGastado = totalGastado;
        this.fechaUltimaVisita = fechaUltimaVisita;
    }

    public String getNombre() {
        return nombre;
    }

    public int getNumVisitas() {
        return numVisitas;
    }

    public double getTotalGastado() {
        return totalGastado;
    }

    public LocalDate getFechaUltimaVisita() {
        return fechaUltimaVisita;
    }
    
    
    
}
