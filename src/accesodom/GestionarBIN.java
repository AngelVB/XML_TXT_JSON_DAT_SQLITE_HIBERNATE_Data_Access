
package accesodom;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * Esta clase contiene todos los métodos necesarios
 * para gestionar ficheros DAT desde nuestro formulario.
 * 
 * @author Pablo Pardo y Ángel Valera
 */
public class GestionarBIN {
   File ficheroBIN=null;
   ArrayList<String> nombre = new ArrayList();
   ArrayList<String> telefono = new ArrayList();
   ArrayList<String> correo = new ArrayList();
   ArrayList<String> nacido = new ArrayList();
   private String contenidoBIN="";
 
   
   /**
    *
    * Método para asignar el fichero DAT enviado por el cuadro de diálogo.
    * @param fichero (File). Archivo seleccionado en el cuadro de diálogo. 
     * @return  True si el fichero se ha abierto correctamente.
    *
    */
   public boolean abrirBIN(File fichero)
    {
         try{
          //  obj=new JSONObject();
             ficheroBIN = fichero.getAbsoluteFile();
             System.out.println(ficheroBIN);
             
            return true;
                                  
        }catch(Exception e){
            return false;
        }
    }
    
    /**
    *
    * Método para recorrer el fichero DAT  e ir montando
    * el string de salida del textarea y los arrays que 
    * utilizaremos para guardar el fichero.
     * @return contenidoBIN (String). Cadena con los datos formateados de nuestros contactos.
     * @throws java.io.IOException
    */
    public String recorrerBINyMostrar() throws IOException{
      contenidoBIN="";
            try
            (RandomAccessFile file = new RandomAccessFile(ficheroBIN,"r")) {
               
                int posicion =0;
           String contacto;
           char nombrechar[] = new char[100];
           char aux;
           char telefonochar[] = new char[100];
           char correochar[] = new char[100];
           char nacidochar[] = new char[100];
           try
           {
               
               for (;;){
                   

                   file.seek(posicion);
                   
                   for (int i=0;i<nombrechar.length;i++) {
                       aux=(char)file.readByte();
                       nombrechar[i]=aux;
                       
                   }
                   
                   String nombres=String.valueOf(nombrechar);
                   System.out.println("Nombres:"+nombres);
                   nombre.add(nombres);
                   
                   for (int i=0;i<telefonochar.length;i++) {
                       aux=(char)file.readByte();
                       telefonochar[i]=aux;
                   }
                   String telefonos=new String(telefonochar);
                   System.out.println("telefonos:"+telefonos);
                   telefono.add(telefonos);
                   for (int i=0;i<correochar.length;i++) {
                       aux=(char)file.readByte();
                       correochar[i]=aux;
                   }
                   String correos=new String(correochar);
                   System.out.println("correos:"+correos);
                   correo.add(correos);
                   for (int i=0;i<nacidochar.length;i++) {
                       aux=(char)file.readByte();
                       nacidochar[i]=aux;
                   }
                   String nacidos=new String(nacidochar);
                   System.out.println("nacidos:"+nacidos);
                   nacido.add(nacidos);
                   
                   contacto= "Persona: "+nombres+"\n"+
                           "    Teléfono: "+telefonos+"\n"+
                           "    Correo: "+correos+"\n"+
                           "    Nacido en: "+nacidos+"\n";
                   
                   contenidoBIN=contenidoBIN + contacto;
                   
                   posicion=posicion + 400;
               }
               
               
           }catch(EOFException eo) {
               
           }
           
          
            }
               return contenidoBIN;
            }
    
   /**
    *
    * Método para añadir datos introducidos en el formulario al fichero DAT y 
    * a la cadena de salida.
    * @param p1 (Persona). Archivo seleccionado en el cuadro de diálogo. 
    * @return True si los datos se han añadido correctamente.
    */
   public Boolean anyadirDatosBIN(persona p1){
       
        String contacto= "Persona: "+p1.getNombre()+"\n"+
                                    "    Teléfono: "+p1.getTelefono()+"\n"+
                                    "    Correo: "+p1.getCorreo()+"\n"+
                                    "    Nacido en: "+p1.getNacido()+"\n";
                     
        setContenidoBIN(getContenidoBIN() + contacto); 
        nombre.add(p1.getNombre());
        telefono.add(p1.getTelefono());
        correo.add(p1.getCorreo());
        nacido.add(p1.getNacido());
      
        return true;
}
   
   /**
    *
    * Método para guardar el fichero salida.dat
     * @return True si el fichero se han guardado correctamente.
     * @throws java.io.IOException
    */
     public boolean guardarBIN() throws IOException{
         
      StringBuffer buffer;
      String n="";
      String t="";
      String c="";
      String nac="";
     int posicion =0;
           try (RandomAccessFile fw = new RandomAccessFile("salida.dat","rw")) {
           
               
            for (int i=0;i<nombre.size();i++){
               fw.seek(posicion);
               buffer=new StringBuffer(nombre.get(i));
                buffer.setLength(100); 
               fw.writeBytes(buffer.toString());
               buffer=new StringBuffer(telefono.get(i));
                buffer.setLength(100); 
                fw.writeBytes(buffer.toString());
               buffer=new StringBuffer(correo.get(i));
                buffer.setLength(100); 
               fw.writeBytes(buffer.toString());
              buffer=new StringBuffer(nacido.get(i));
               buffer.setLength(100); 
               fw.writeBytes(buffer.toString());     
               posicion=posicion+400;
            }
            fw.close();
            return true;
           
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GestionarBIN.class.getName()).log(Level.SEVERE, null, ex);
            return false;
        }
   
    }  

    /**
     * @return the contenidoBIN
     */
    public String getContenidoBIN() {
        return contenidoBIN;
    }

    /**
     * @param contenidoBIN the contenidoBIN to set
     */
    public void setContenidoBIN(String contenidoBIN) {
        this.contenidoBIN = contenidoBIN;
    }
}
