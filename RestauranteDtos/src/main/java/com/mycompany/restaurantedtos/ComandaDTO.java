/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedtos;

import java.time.LocalDateTime;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ComandaDTO {
    private String folio;
    private LocalDateTime fechaHora;
    private Double total;
    private Long idMesa;
    private Long idCliente;
    private String estado;

    public ComandaDTO() {
    }
    
    public ComandaDTO(Long idMesa, Long idCliente) {
        this.idMesa    = idMesa;
        this.idCliente = idCliente;
    }

    public ComandaDTO(String folio, LocalDateTime fechaHora, Double total, Long idMesa, Long idCliente, String estado) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.idMesa = idMesa;
        this.idCliente = idCliente;
        this.estado = estado;
    }

    public ComandaDTO(String folio, LocalDateTime fechaHora, Double total, String estado) {
        this.folio = folio;
        this.fechaHora = fechaHora;
        this.total = total;
        this.estado = estado;
    }
    
    public ComandaDTO(Double total) {
        this.total = total;
    }
    

    public String getFolio() {
        return folio;
    }

    public Long getIdMesa() {
        return idMesa;
    }

    public Long getIdCliente() {
        return idCliente;
    }

    public String getEstado() {
        return estado;
    }

    public LocalDateTime getFechaHora() {
        return fechaHora;
    }

    public Double getTotal() {
        return total;
    }
    
    
  
}
