package accesodom;

import com.sun.org.apache.xml.internal.serialize.OutputFormat;
import com.sun.org.apache.xml.internal.serialize.XMLSerializer;
import java.io.*;
import java.util.ArrayList;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerFactory;
import org.w3c.dom.*;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.xml.sax.SAXException;

/**
 * 
 * @author Pablo Pardo y Ángel Valera
 * Clase GestionarDOM: Clase que permite gestionar los archivos XML
 * Cuenta con los métodos: Abrir, guardar, Añadir y recorrer:
 * 
 */
public class GestionarDOM {

    Document doc; //El objeto de tipo document almacena el documento seleccionado.
    private String contenidoXML="";//Este atributo guarda el contenido del XML en String para luego mostrarlo.
    private String contacto;
    //arrayList donde almacenamos el contenido del XML en formato de objeto de tipo persona.
    ArrayList<persona> listaPersonas = new ArrayList<>();

    public String getContacto() {
        return contacto;
    }
    /**
     * Abre el fichero XML que se le pasa por parámetro. Se le asigna al atributo Doc.
     * @param fichero
     * @return true o false si funciona correctamente o no.
     */
    public boolean abrir_XML_DOM(File fichero) {

        doc = null;

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            factory.setIgnoringComments(true);
            factory.setIgnoringElementContentWhitespace(true);
            DocumentBuilder builder = factory.newDocumentBuilder();
            doc = builder.parse(fichero);
            //recorrerArbol();
            return true;

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Recorre el árbol, lee el XML y extrae su contenido, guardándolo en un array de personas.
     * Además monta el string que mostrará en pantalla. (contenidoXML)
     * @return String con el contenido actual del XML
     * @throws SAXException
     * @throws ParserConfigurationException
     * @throws IOException 
     */
     public String recorrerArbol() throws SAXException, ParserConfigurationException, IOException{
     contenidoXML="";
     try {
  File file = new File("salida.xml");
  DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
  DocumentBuilder db = dbf.newDocumentBuilder();
   doc = db.parse(file);
  listaPersonas = new ArrayList<>();
  doc.getDocumentElement().normalize();
  NodeList nodeLst = doc.getElementsByTagName("Persona");

  for (int s = 0; s < nodeLst.getLength(); s++) {

    Node primerNodo = nodeLst.item(s);
    
    if (primerNodo.getNodeType() == Node.ELEMENT_NODE) {
        //Extraigo los nodos del XML
      Element fstElmnt = (Element) primerNodo;
      String XMLNombre=(fstElmnt.getElementsByTagName("Nombre").item(0).getTextContent());
      String XMLTel = (fstElmnt.getElementsByTagName("Telefono").item(0).getTextContent());
      String XMLCorreo= (fstElmnt.getElementsByTagName("Correo").item(0).getTextContent());
      String XMLNacido =( fstElmnt.getAttribute("nacido_en"));
      //Creo un objeto persona y le paso los valores
      persona p1=new persona();
      p1.setNombre(XMLNombre);
      p1.setTelefono(XMLTel);
      p1.setCorreo(XMLCorreo);
      p1.setNacido(XMLNacido);
      //guardo la persona al AL de personas
      listaPersonas.add(p1);
      //formateo la salida
      
      String nuevaPersona =  "Persona: " + XMLNombre + "\n"
                + "    Teléfono: " + XMLTel + "\n"
                + "    Correo: " + XMLCorreo + "\n"
                + "    Nacido en: " + XMLNacido + "\n";
      contenidoXML=contenidoXML+nuevaPersona;
    
    }//fin if

  }//fin for
         System.out.println("salgo for");
      System.out.println(contenidoXML);
      return contenidoXML;
  } catch (Exception e) {
      
  }
         System.out.println("salgo funcion");
         return contenidoXML;
         
     }//fin método
     /**
      * Guardar XML: Crea el documento XML.
      *  itera la lista de objetos persona, extrayendo sus atributos, y los añade como elementos al XML.
      * Para finalizar crea el XML con esos nodos, y lo guarda en salida.xml
      * @return boolean si funciona o no
      * @throws ParserConfigurationException
      * @throws TransformerConfigurationException
      * @throws TransformerException 
      */
    public boolean guardarXML() throws ParserConfigurationException, TransformerConfigurationException, TransformerException {
        
        //monto el xml y voy añadiendo los datos de pesrona
        File f = new File("salida.xml");
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        DOMImplementation imple = builder.getDOMImplementation();
        doc = imple.createDocument(null, "Agenda", null);
        //Itero en el AL de personas
        for (persona miPersona : listaPersonas) {
            Element rootEl = doc.createElement("Persona");
            rootEl.setAttribute("nacido_en", miPersona.getNacido());
            doc.getDocumentElement().appendChild(rootEl);
            Element nom = doc.createElement("Nombre");
            Text valor = doc.createTextNode(miPersona.getNombre());
            rootEl.appendChild(nom);
            nom.appendChild(valor);
            //Creo los nodos y les doy valor
            Element tel = doc.createElement("Telefono");
            Text valorTel = doc.createTextNode(miPersona.getTelefono());
            rootEl.appendChild(tel);
            tel.appendChild(valorTel);
            
            Element cor = doc.createElement("Correo");
            Text valorCor = doc.createTextNode(miPersona.getCorreo());
            rootEl.appendChild(cor);
            cor.appendChild(valorCor);
            
        }
        Source source = new DOMSource(doc);
        File fichero = new File("salida.xml");
        Result result = new StreamResult(fichero);

        try {

            TransformerFactory tf = TransformerFactory.newInstance();
            Transformer trans = tf.newTransformer();
            trans.transform(source, result);
            factory.setIgnoringComments(true);
           factory.setIgnoringElementContentWhitespace(true);
   
        }catch(TransformerException e){}
return true;
    }

/**
 * Añadir datos.
 * Recibe una persona del formulario, y va añadiendo sus atributos a la cadena.
 * Además la añade al ArrayList de personas para que quede actualizado.
 * @param p1 (Objeto de tipo persona)
 * @return boolean
 */
    public boolean anyadirDatosXML(persona p1) {
        String nombre, correo, telefono, nacido;
        String contacto2;
        nombre = p1.getNombre();
        correo = p1.getCorreo();
        telefono = p1.getTelefono();
        nacido = p1.getNacido();

        contacto2 = "Persona: " + nombre + "\n"
                + "    Teléfono: " + telefono + "\n"
                + "    Correo: " + correo + "\n"
                + "    Nacido en: " + nacido + "\n";

        setContenidoXML(getContenidoXML() + contacto2);
        listaPersonas.add(p1);

        return true;
    }

    /**
     * @return the contenidoXML
     */
    public String getContenidoXML() {
        return contenidoXML;
    }

    /**
     * @param contenidoXML the contenidoXML to set
     */
    public void setContenidoXML(String contenidoXML) {
        this.contenidoXML = contenidoXML;
    }

    
}
