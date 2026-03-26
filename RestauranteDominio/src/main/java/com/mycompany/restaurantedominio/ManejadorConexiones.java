/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.restaurantedominio;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 *
 * @author PC GAMER MASTER RACE
 */
public class ManejadorConexiones {
    public static EntityManager crearEntityManager(){
        //Fabrica que permite construi entity manager a partir de ciertas configuraciones
        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("RestauranteDominioPU");
        //Objeto que permite hacer operaciones de bd
        EntityManager entityManager = emFactory.createEntityManager();
        return entityManager;
    }
    
}
