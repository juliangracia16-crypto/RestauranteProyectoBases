/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedominio;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 *
 * @author PC GAMER MASTER RACE
 */
@Entity
@Table(name = "clientes_frecuentes")
@DiscriminatorValue("FRECUENTE")
public class ClienteFrecuente extends Cliente implements Serializable {

    @Column(name = "telefono", nullable = false, length = 255)
    private String telefono;
    
    @Column(name = "correo")
    private String correo;

    @Column(name = "fecha_registro", nullable = false)
    private LocalDate fechaRegistro;

    public ClienteFrecuente() {
    }

    public ClienteFrecuente(String nombre, String telefono, String correo) {
        super(nombre);
        this.telefono      = telefono;
        this.correo        = correo;
        this.fechaRegistro = LocalDate.now();
    }
    
    //Como es un atributo derivado osea que no se guarda en la BD 
    public double getTotalGastado() {
        double total = 0;
        if (getComandas() != null) {
            for (Comanda c : getComandas()) {
                if (c.getEstado() != EstadoComanda.CANCELADA) {
                    total += c.getTotal();
                }
            }
        }
        return total;
    }
    
    //Como es un atributo derivado osea que no se guarda en la BD 
    public int getNumVisitas() {
        int visitas = 0;
        if (getComandas() != null) {
            for (Comanda c : getComandas()) {
                if (c.getEstado() != EstadoComanda.CANCELADA) {
                    visitas++;
                }
            }
        }
        return visitas;
    }
    
    //Como es un atributo derivado osea que no se guarda en la BD 
    public int getPuntosAcumulados() {
        return (int) (getTotalGastado() / 20);
    }
    
    public LocalDate getFechaUltimaVisita() {
    LocalDate ultima = null;
    if (getComandas() != null) {
        for (Comanda c : getComandas()) {
            if (c.getEstado() != EstadoComanda.CANCELADA) {
                LocalDate fecha = c.getFechaHora().toLocalDate();
                if (ultima == null || fecha.isAfter(ultima)) {
                    ultima = fecha;
                }
            }
        }
    }
    return ultima;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
    
     @Override
    public int hashCode() {
        int hash = 0;
        hash += (getIdCliente() != null ? getIdCliente().hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ClienteFrecuente)) return false;
        ClienteFrecuente other = (ClienteFrecuente) object;
        if ((this.getIdCliente() == null && other.getIdCliente() != null) ||
            (this.getIdCliente() != null && !this.getIdCliente().equals(other.getIdCliente()))) return false;
        return true;
    }

    @Override
    public String toString() {
        return "ClienteFrecuente[ id=" + getIdCliente() + " ]";
    }
    
    
    
    
    
    
}
