/*
 *Clase para crear el objeto base de datos a la que conectarse.
 */
package bd.models;

/**
 *
 * @author Pablo Pardo y Ángel Valera
 */
public class basedatos {
    public String nombre;
    
    /**
     * Constructor con todos los parámetros
     * @param nombre Nombre de la tabla
     * 
     */
    public basedatos(String nombre){
        this.nombre=nombre;
        
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
    
    
}
