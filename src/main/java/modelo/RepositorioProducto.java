/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package modelo;

import modelo.Producto;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author stive
 */
public interface RepositorioProducto extends CrudRepository<Producto,Long>{
    
}
