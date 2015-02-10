/*
 * Clase que controlará los objetos de tipo campo, su nombre, tipo, y si es unico, clave primaria o no nulo.
 */
package bd.models;

/**
 *
 * @author Pablo Pardo y Ángel Valera
 */
public class campo {
        public String nombre;
        public String tipo;
        private int longitud;
        int id=0;
        //restricciones
        public int primary=0;
        public int unique = 0;
        public int notNull=0; //tue= es not null
 
/**
 * Constructor que crea un campo vacío
 */
public campo(){
    this.nombre="";
        this.tipo="";
        this.primary=0;
        this.unique=0;
        this.notNull=0;
};
        
/**
  Constructor con todos los paráḿetros
 * @param nombre nombre del campo
 * @param tipo String que define el tipo de datos
 * @param primary variable entera que simula el booleano true o falso
 * @param unique  variable entera que simula el booleano true o falso
 * @param notNull  variable entera que simula el booleano true o falso
 */
        public campo(String nombre, String tipo, int primary, int unique, int notNull ){
        this.nombre=nombre;
        this.tipo=tipo;
        this.primary=primary;
        this.unique=unique;
        this.notNull=notNull;
    }
    

    /**
     * Método para modificar el campo, cambia el valor del nombre y del tipo por los nuevos pasados
     * @param nombreViejo
     * @param nombreNuevo
     * @param tipoNuevo 
     */
    public void modificarCampo(String nombreViejo, String nombreNuevo, String tipoNuevo){
        this.setNombre(nombreNuevo);
        this.setTipo(tipoNuevo);
    }

    /**
     * @return  nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @return  tipo
     */
    public String getTipo() {
        return tipo;
    }

    /**
     * @return  longitud
     */
    public int getLongitud() {
        return longitud;
    }

    /**
     * @return  primary
     */
    public int getPrimary() {
        return primary;
    }

    /**
     * @param primary the primary to set
     */
    public void setPrimary(int primary) {
        this.primary = primary;
    }

    /**
     * @return  unique
     */
    public int getUnique() {
        return unique;
    }

    /**
     * @param unique 
     */
    public void setUnique(int unique) {
        this.unique = unique;
    }

    /**
     * @return  notNull
     */
    public int getNotNull() {
        return notNull;
    }

    /**
     * @param notNull 
     */
    public void setNotNull(int notNull) {
        this.notNull = notNull;
    }

    /**
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param tipo 
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}
