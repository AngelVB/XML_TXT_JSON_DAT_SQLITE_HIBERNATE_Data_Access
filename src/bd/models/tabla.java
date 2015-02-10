/*
 * Clase que controlará los objetos de tipo tabla.
Como atributos tendrá el nombre de la tabla y una lista de campos (objetos campo)
 */
package bd.models;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pablo Pardo y Ángel Valera
 */
public class tabla {
    public String nombre;
    public List<campo> campos = new ArrayList<>();

    
    /**
     * Constructor con todos los parámetros
     * @param nombre Nombre de la tabla
     * @param campos Lista de objetos de tipo campo
     * 
     */
    public tabla(String nombre,  List<campo> campos){
        this.setNombre(nombre);
        this.setCampos(campos);     
  }  
   
   /**
     * 
     * @param nombre Nombre de la tabla
     * @parurn query: El query que ejecutará en la bbdd
     * 
     */
    public tabla(String nombre){
        this.setNombre(nombre);
        this.setCampos(null);
       
  }
    
    /**
     * Método para crear las tablas físicas
     * @return devuelve un String con la sentencia SQL del create.
     */
     public String crearTabla(){
        String query="";
        String principio="CREATE table "+nombre+ " ( ";
        String fin=" );";
         
        if(campos!=null)
        { 
        if (campos.size()>0 || !campos.isEmpty())
        {            
            for (int i = 0; i < campos.size(); i++){ 
          String campoString=campos.get(i).getNombre();
          String tipoString=campos.get(i).getTipo();
          int primary=campos.get(i).getPrimary();
          int notnull=campos.get(i).getNotNull();
          int unique=campos.get(i).getUnique();
          if (primary==1){
              tipoString=tipoString+ " PRIMARY KEY AUTOINCREMENT";
          }
         
          if (notnull==1){
              tipoString=tipoString+ " NOT NULL";
          }
          
           if (unique==1){
              tipoString=tipoString+ " UNIQUE";
          }
          
          if (i==campos.size()-1){ //si es el último, no cogerá la coma
           principio=principio+" "+campoString+" "+tipoString;
          }
          else{ //imprimirá los nombres de todos los demás, con una coma
            principio=principio+" "+campoString +" "+tipoString+", ";
            }
         }
 
        }
        }else{
        principio=principio+" ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL";
        
        }
          query=principio+fin;
          
         System.out.println("Nueva tabla:"+query);
         return query;
    }

    /**
     * @return nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return campo
     */
    public List<campo> getCampos() {
        return campos;
    }

    /**
     * @param campos
     */
    public void setCampos(List<campo> campos) {
        this.campos = campos;
    }
}