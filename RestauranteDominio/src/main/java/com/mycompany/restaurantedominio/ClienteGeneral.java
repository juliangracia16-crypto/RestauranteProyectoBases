/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedominio;

import java.io.Serializable;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 *
 * @author PC GAMER MASTER RACE
 */
@Entity
@Table(name = "clientes_general")
@DiscriminatorValue("GENERAL")
public class ClienteGeneral extends Cliente implements Serializable {

    public ClienteGeneral() {
        super("Cliente General");
    }

    public String getNombreCompleto() {
        return "Cliente General";
    }

    @Override
    public String toString() {
        return "ClienteGeneral[ id=" + getIdCliente()+ " ]";
    }    
    
}
