/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 *
 * @author stive
 */
@Table("productos")
public class Producto {
    @Id
    @Column("codigo")
    private Long codigo;
    @Column("nombre")
    private String nombre;
    @Column("precio")
    private float precio;
    @Column("inventario")
    private int inventario;
    
    public Producto(Long codigo, String nombre, float precio, int inventario){
        this.codigo = codigo;
        this.nombre = nombre;
        this.precio = precio;
        this.inventario = inventario;
    }
    @Override
    public String toString(){
        return this.getNombre() +"|"+ this.getPrecio()+"|"+this.getInventario();
    }
    
    public static Producto crearProducto(String nombre,  float precio, int inventario ){
        return new Producto(null,nombre,precio,inventario);
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the precio
     */
    public float getPrecio() {
        return precio;
    }

    /**
     * @param precio the precio to set
     */
    public void setPrecio(float precio) {
        this.precio = precio;
    }

    /**
     * @return the inventario
     */
    public int getInventario() {
        return inventario;
    }

    /**
     * @param inventario the inventario to set
     */
    public void setInventario(int inventario) {
        this.inventario = inventario;
    }

    /**
     * @return the codigo
     */
    public Long getCodigo() {
        return codigo;
    }
}
