
package com.mycompany.restaurantenegocio;

/**
 *
 * @author Julian
 */
public class ObjetosBoDTO {
    private final IClienteBO clienteBO;
    private final IComandaBO comandaBO;
    private final IMesaBO mesaBO;
    private final IProductoBO productoBO;
    private final IReporteClientesFrecuentesBO reporteBO;
    private final IIngredientesBO ingredientesBO;
    private final IProductoSeleccionadoBO productoSeleccionadoBO;
    
    public ObjetosBoDTO(IClienteBO clienteBO, IComandaBO comandaBO, IMesaBO mesaBO, IProductoBO productoBO, IReporteClientesFrecuentesBO reporteBO, IIngredientesBO ingredientesBO, IProductoSeleccionadoBO productoSeleccionadoBO) {
        this.clienteBO = clienteBO;
        this.comandaBO = comandaBO;
        this.mesaBO = mesaBO;
        this.productoBO = productoBO;
        this.reporteBO = reporteBO;
        this.ingredientesBO = ingredientesBO;
        this.productoSeleccionadoBO = productoSeleccionadoBO;
    }

    public IClienteBO getClienteBO() {
        return clienteBO;
    }

    public IComandaBO getComandaBO() {
        return comandaBO;
    }

    public IMesaBO getMesaBO() {
        return mesaBO;
    }

    public IProductoBO getProductoBO() {
        return productoBO;
    }

    public IReporteClientesFrecuentesBO getReporteBO() {
        return reporteBO;
    }

    public IIngredientesBO getIngredientesBO() {
        return ingredientesBO;
    }
    
    public IProductoSeleccionadoBO getProductoSeleccionadoBO(){
        return productoSeleccionadoBO;
    }
    
}
