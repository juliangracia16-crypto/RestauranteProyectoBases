
package com.mycompany.restaurantedtos;

/**
 *
 * @author Julian
 */
public class NuevoIngredienteDTO {
    private String nombre;
    private Integer stockInicial;
    private UnidadMedida unidadMedida;

    public NuevoIngredienteDTO(String nombre, Integer stockInicial, UnidadMedida unidadMedida) {
        this.nombre = nombre;
        this.stockInicial = stockInicial;
        this.unidadMedida = unidadMedida;
    }

    public String getNombre() {
        return nombre;
    }

    public Integer getStockInicial() {
        return stockInicial;
    }

    public UnidadMedida getUnidadMedida() {
        return unidadMedida;
    }
    
}
