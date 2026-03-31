package com.mycompany.restaurantedtos;

public class ProductoIngredienteDTO {

    private Long idIngrediente;
    private Integer cantidad;

    public ProductoIngredienteDTO() {
    }

    public ProductoIngredienteDTO(Long idIngrediente, Integer cantidad) {
        this.idIngrediente = idIngrediente;
        this.cantidad = cantidad;
    }

    public Long getIdIngrediente() {
        return idIngrediente;
    }

    public Integer getCantidad() {
        return cantidad;
    }
}
