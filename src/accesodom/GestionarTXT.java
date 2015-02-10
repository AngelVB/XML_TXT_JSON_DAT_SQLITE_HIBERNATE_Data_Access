package accesodom;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Clase que permite gestionar ficheros TXT Con esta clase abriremos,
 * recorreremos y mostraremos los ficheros.
 *
 * @author Pablo Pardo y Ángel Valera.
 */
public class GestionarTXT {

    private File ficheroTXT;
    private String contenidoTXT = "";
    private final FileReader fr = null;
    private final BufferedReader br = null;

    /**
     * Abrir fichero
     * Este método abre el fichero que se le pasa por parámetro, en caso de que exista.
     * @param f1  File
     * @return true o false, si funciona o no.
     */
    public boolean abrir_TXT(File f1) {
        try {
            ficheroTXT = f1;
            return true;
        } catch (Exception e) {
            return false;
        }
    }
/**
 * Lee el archivo TXT y lo muestra por pantalla.
 * @return String con el contenido del TXT
 * @throws IOException 
 */
    public String recorrerTXTyMostrar() throws IOException {
        BufferedReader br;
        br = new BufferedReader(new FileReader(ficheroTXT));
        try {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = br.readLine();
            }
            setContenidoTXT(sb.toString());
            br.close();
            return getContenidoTXT();
        } catch (Exception e) {
            return "Error al mostrar fichero";
        }

    }
/**
 * Añade una persona al TXT
 * Recibe como parámetro una persona, de la cuál extrae sus atributos, y los añade al String del TXT, formateándolo.
 * Actualiza el valor del atributo contenidoTXT al finalizar, y devuelve true.
 * @param p1
 * @return boolean
 */
    public boolean anyadirTXT(persona p1) {
        String nombre, numero, correo, nacido, contacto;
        nombre = p1.getNombre();
        numero = p1.getTelefono();
        correo = p1.getCorreo();
        nacido = p1.getNacido();

        contacto = "Persona: " + nombre + "\n"
                + "    Teléfono: " + numero + "\n"
                + "    Correo: " + correo + "\n"
                + "    Nacido en: " + nacido + "\n";
        contenidoTXT = contenidoTXT + contacto;

        return true;
    }
/**
 * Guarda el contenido del atributo en un archivo TXT.
 * Esta sobreescribe el fichero si ya existía, dejándolo siempre actualizado.
 * @return boolean
 * @throws FileNotFoundException 
 */
    public boolean guardarTXT() throws FileNotFoundException {

        try {
            File fichero = new File("salida.txt");
            FileWriter w = new FileWriter(fichero);
            BufferedWriter bw = new BufferedWriter(w);
            PrintWriter wr = new PrintWriter(bw);
            wr.write(getContenidoTXT());//escribimos en el archivo
            wr.close();
            bw.close();
            return true;
        } catch (IOException e) {
            return false;
        }

    }

    /**
     * @return the contenidoTXT
     */
    public String getContenidoTXT() {
        return contenidoTXT;
    }

    /**
     * @param contenidoTXT the contenidoTXT to set
     */
    public void setContenidoTXT(String contenidoTXT) {
        this.contenidoTXT = contenidoTXT;
    }
}
