/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedtos;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ClienteFrecuenteDTO {
    private Long idCliente;
    private String nombre;
    private String telefono;
    private String correo;

    public ClienteFrecuenteDTO() {
    }

    public ClienteFrecuenteDTO(Long idCliente, String nombre, String telefono, String correo) {
        this.idCliente = idCliente;
        this.nombre = nombre;
        this.telefono = telefono;
        this.correo = correo;
    }
    
    
    public ClienteFrecuenteDTO(String nombre, String telefono, String correo) {
        this.nombre   = nombre;
        this.telefono = telefono;
        this.correo   = correo;
    }

    public Long getIdCliente() {
        return idCliente;
    }
    
    //Se necesita para que en la dto se envie el telefono encriptado
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }
  
    public String getNombre() {
        return nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public String getCorreo() {
        return correo;
    }
    
    
    
}
