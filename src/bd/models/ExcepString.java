/*
Excepcion creada para capturar el error de inserciones de datos de texto en campos numéricos
 */
package bd.models;

import javax.swing.JOptionPane;

/**
 *
 * @author Pablo Pardo y Ángel Valera.
 */
public class ExcepString extends Exception {

    public ExcepString(String cadena) {
        JOptionPane.showMessageDialog(null, "No puede insertar texto en campo numérico", "InfoBox: " + "Error", JOptionPane.INFORMATION_MESSAGE);
        throw new UnsupportedOperationException("Debe insertar texto"); 
    }

  // System.out.println("Entro mi excepcion");    

    public ExcepString() {
        throw new UnsupportedOperationException("Not supported yet.");
    }
}


