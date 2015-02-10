package accesodom;

/**
 *
 * Esta clase contiene todos los atributos de nuestros contactos.
 * 
 * @author Pablo Pardo y Ángel Valera
 */
public class persona {
    private String nombre;
    private String telefono;
    private String correo;
    private String nacido;
    
    public persona(String nombre, String telefono, String correo, String nacido){
        this.nombre=nombre;
        this.telefono=telefono;
        this.correo=correo;
        this.nacido=nacido;
    }

    persona() {
    nombre="";
    telefono="";
    correo="";
    nacido="";
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
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the nacido
     */
    public String getNacido() {
        return nacido;
    }

    /**
     * @param nacido the nacido to set
     */
    public void setNacido(String nacido) {
        this.nacido = nacido;
    }
    
   
    
    
}
