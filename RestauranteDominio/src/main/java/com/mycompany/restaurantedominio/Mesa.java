/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedominio;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author PC GAMER MASTER RACE
 */
@Entity
@Table(name = "mesas")
public class Mesa implements Serializable {

    @Id
    @Column(name = "id_mesa")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "numero_mesa" , nullable = false, unique = true)
    private Integer numeroMesa;
    
    @Column(name = "disponibilidad", nullable = false)
    @Enumerated(EnumType.STRING)
    private DisponibilidadMesa disponibilidad;

    public Mesa() {
    }
    
    public Mesa(Integer numeroMesa, DisponibilidadMesa disponibilidad) {
        this.numeroMesa = numeroMesa;
        this.disponibilidad = disponibilidad;
    }
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public DisponibilidadMesa getDisponibilidad() {
        return disponibilidad;
    }

    public void setDisponibilidad(DisponibilidadMesa disponibilidad) {
        this.disponibilidad = disponibilidad;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof Mesa)) {
            return false;
        }
        Mesa other = (Mesa) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Mesa[ id=" + id + " ]";
    }
    
}
