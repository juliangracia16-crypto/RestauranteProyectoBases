
package com.mycompany.restaurantenegocio;

import com.mycompany.restaurantedominio.Ingrediente;
import com.mycompany.restaurantedtos.ActualizarIngredienteDTO;
import com.mycompany.restaurantedtos.NuevoIngredienteDTO;
import com.mycompany.restaurantepersistencia.IIngredientesDAO;
import com.mycompany.restaurantepersistencia.PersistenciaException;
import java.util.List;

/**
 *
 * @author Julian
 */
public class IngredientesBO implements IIngredientesBO{
    private final IIngredientesDAO ingredientesDAO;

    public IngredientesBO(IIngredientesDAO ingredientesDAO) {
        this.ingredientesDAO = ingredientesDAO;
    }
    
    @Override
    public Ingrediente registrarIngrediente(NuevoIngredienteDTO ingrediente) throws NegocioException {
        try{
            if(ingrediente.getNombre() == null){
                throw new NegocioException("El nombre no puede estar vacio.");
            }
            if(ingrediente.getNombre().length() > 100){
                throw new NegocioException("El nombre debe tener como maximo 100 caracteres.");
            }
            if(ingrediente.getStockInicial() == null){
                throw new NegocioException("El stock inicial no debe ser nulo.");
            }
            if(ingrediente.getStockInicial() < 0){
                throw new NegocioException("El stock inicial tiene que ser minimo de 1.");
            }
            if(ingrediente.getUnidadMedida() == null){
                throw new NegocioException("La unidad de medida no puede ser nula.");
            }
            List<NuevoIngredienteDTO> ingredientes = ingredientesDAO.consultarIngredientesDTO();
            for(NuevoIngredienteDTO i: ingredientes){
                if(i.getNombre().trim().equals(ingrediente.getNombre().trim()) && i.getUnidadMedida() == ingrediente.getUnidadMedida()){
                    throw new NegocioException("El ingrediente ya se encuentra registrado.");
                }
            }    
            Ingrediente nuevoIngrediente = ingredientesDAO.registrarIngrediente(ingrediente);
            return nuevoIngrediente;
        }catch(PersistenciaException ex){
            throw new NegocioException("No se pudo registrar el ingrediente.",ex);
        }
    }

    @Override
    public Ingrediente agregarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws NegocioException {
        try{
            if(ingrediente.getIdIngrediente() == null){
                throw new NegocioException("El ID no puede ser nulo.");
            }
            if(ingrediente.getCantidad() == null){
                throw new NegocioException("La cantidad a agregar no debe ser nula.");
            }
            if(ingrediente.getCantidad() < 0){
                throw new NegocioException("La cantidad a agregar debe ser mayor a 0.");
            }
            Ingrediente ingredienteActualizado = ingredientesDAO.agregarStockIngrediente(ingrediente);
            return ingredienteActualizado;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al agregar el stock al ingrediente.",ex);
        }
    }

    @Override
    public Ingrediente quitarStockIngrediente(ActualizarIngredienteDTO ingrediente) throws NegocioException {
        try{
            if(ingrediente.getIdIngrediente() == null){
                throw new NegocioException("El ID no puede ser nulo.");
            }
            if(ingrediente.getCantidad() == null){
                throw new NegocioException("La cantidad a agregar no debe ser nula.");
            }
            if(ingrediente.getCantidad() < 0){
                throw new NegocioException("La cantidad a agregar debe ser mayor a 0.");
            }
            Ingrediente ingredienteAActualizar = ingredientesDAO.consultarIngredientePorId(ingrediente.getIdIngrediente());
            Integer cantidadTotal = ingrediente.getCantidad() - ingredienteAActualizar.getStock();
            if(cantidadTotal < 0){
                throw new NegocioException("No se puede quitar mas del stock actual que se tiene.");
            }
            Ingrediente ingredienteActualizado = ingredientesDAO.agregarStockIngrediente(ingrediente);
            return ingredienteActualizado;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al quitar el stock al ingrediente.",ex);
        }
    }

    @Override
    public Ingrediente consultarIngredientePorId(Long id) throws NegocioException {
        try{
            if(id == null){
                throw new NegocioException("El ID no puede ser nulo.");
            }
            if(id < 0){
                throw new NegocioException("El ID debe de ser un numero positivo.");
            }
            Ingrediente ingredienteEncontrado = ingredientesDAO.consultarIngredientePorId(id);
            return ingredienteEncontrado;
        }catch(PersistenciaException ex){
            throw new NegocioException("Error al quitar el stock al ingrediente.",ex);
        }
    }
    
}
