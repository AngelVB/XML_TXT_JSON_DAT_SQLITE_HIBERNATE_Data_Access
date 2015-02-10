package accesodom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

/**
 *
 * Esta clase contiene todos los métodos necesarios
 * para gestionar ficheros JSON desde nuestro formulario.
 * 
 * @author Pablo Pardo y Ángel Valera
 */
public class gestionarJSON {

    File ficheroJSON;
    JSONObject obj;
    private String textoJSON = "[]";
    private String contenidoJSON = "";

    
    /**
    *
    * Método para asignar el fichero JSON enviado por el cuadro de diálogo.
    * @param fichero (File). Archivo seleccionado en el cuadro de diálogo. 
     * @return  True si el fichero se abre correctamente.
    *
    */
    public boolean abrirJSON(File fichero) {
        try {
            //  obj=new JSONObject();
            ficheroJSON = fichero;
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    /**
    *
    * Método para leer el fichero JSON y almacenarlo en una variable.
    *
     * @throws java.io.IOException
    */
    public void leerJSON() throws IOException {
        StringBuilder fileData = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new FileReader(ficheroJSON))) {
            char[] buf = new char[1024];
            int numRead = 0;
            while ((numRead = reader.read(buf)) != -1) {
                String readData = String.valueOf(buf, 0, numRead);
                fileData.append(readData);
            }
        }
        setTextoJSON(fileData.toString());

    }

    /**
    *
    * Método para recorrer la cadena JSON del fichero e ir montando
    * el string de salida del textarea y los arrays que 
    * utilizaremos para guardar el fichero.
     * @return contenidoJSON (String). Cadena con los datos formateados de nuestros contactos.
    */
    public String recorrerJSONyMostrar() {
        contenidoJSON = "";
        String s = textoJSON;
        Object obj1 = JSONValue.parse(s);
        JSONArray array = (JSONArray) obj1;

        for (Object array1 : array) {
            JSONObject obj2 = (JSONObject) array1;
            String contacto = "Persona: " + obj2.get("nombre") + "\n"
                    + "    Teléfono: " + obj2.get("telefono") + "\n"
                    + "    Correo: " + obj2.get("correo") + "\n"
                    + "    Nacido en: " + obj2.get("nacido") + "\n";
            setContenidoJSON(getContenidoJSON() + contacto);
        }

        return getContenidoJSON();
    }
    
    /**
    *
    * Método para añadir datos introducidos en el formulario al fichero JSON y 
    * a la cadena de salida.
    * @param p1 (Persona). Archivo seleccionado en el cuadro de diálogo. 
     * @return  True si los datos se han añadido correctamente.
    */
    public Boolean anyadirDatosJSON(persona p1) {
        Map m1 = new LinkedHashMap();

        Object obj1 = JSONValue.parse(getTextoJSON());
        JSONArray array = (JSONArray) obj1;

        m1.put("nombre", p1.getNombre());
        m1.put("telefono", p1.getTelefono());
        m1.put("correo", p1.getCorreo());
        m1.put("nacido", p1.getNacido());
        array.add(m1);
        textoJSON = array.toString();
        System.out.println(textoJSON);
        String contacto = "Persona: " + p1.getNombre() + "\n"
                + "    Teléfono: " + p1.getTelefono() + "\n"
                + "    Correo: " + p1.getCorreo() + "\n"
                + "    Nacido en: " + p1.getNacido() + "\n";

        setContenidoJSON(getContenidoJSON() + contacto);
        return true;
    }

    /**
    *
    * Método para guardar el fichero salida.json
     * @return True si el fichero se ha guardado correctamente.
     * @throws java.io.IOException
    */
    public boolean guardarJSON() throws IOException {
        //ficheroJSON;
        File file = new File("salida.json");
        // if file doesnt exists, then create it
        try {

            file.createNewFile();

            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            try (BufferedWriter bw = new BufferedWriter(fw)) {
                bw.write(textoJSON);
            }
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    /**
     * @return the textoJSON
     */
    public String getTextoJSON() {
        return textoJSON;
    }

    /**
     * @param textoJSON the textoJSON to set
     */
    public void setTextoJSON(String textoJSON) {
        this.textoJSON = textoJSON;
    }

    /**
     * @return the contenidoJSON
     */
    public String getContenidoJSON() {
        return contenidoJSON;
    }

    /**
     * @param contenidoJSON the contenidoJSON to set
     */
    public void setContenidoJSON(String contenidoJSON) {
        this.contenidoJSON = contenidoJSON;
    }

}
