
package hib.modelo;

import hib.modelo.Contacto;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los modelos de las listas de Amigos.
 * Utilizada para poder alamcenar Objetos Amigo en un JList
 */
public class ListModelAmigo extends AbstractListModel{

    private ArrayList<Contacto> Amigos = new ArrayList<>();
    
    @Override
    public int getSize() {
       return Amigos.size();
     }

    @Override
    public Object getElementAt(int index) {
     Contacto c = Amigos.get(index);
   return c.getUser();   
    
    }
    
    public void addAmigo(Contacto c){
  Amigos.add(c);
  this.fireIntervalAdded(this, getSize(), getSize()+1);
 }
    
    public void eliminarPersona(int index0){
   Amigos.remove(index0);
   this.fireIntervalRemoved(index0, getSize(), getSize()+1);
 }
    public Contacto getAmigo(int index){
  return Amigos.get(index);
}

}