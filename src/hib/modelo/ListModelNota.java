
package hib.modelo;

import hib.modelo.Nota;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los modelos de las listas de notas.
 * Utilizada para poder alamcenar Objetos Nota en un JList
 */
public class ListModelNota extends AbstractListModel{

    private ArrayList<Nota> Notas = new ArrayList<>();
    
    @Override
    public int getSize() {
       return Notas.size();
     }

    @Override
    public Object getElementAt(int index) {
     Nota n = Notas.get(index);
   return n.getResumen();   
    
    }
    
    public void addNota(Nota n){
  Notas.add(n);
  this.fireIntervalAdded(this, getSize(), getSize()+1);
 }
    
    public void eliminarNota(int index0){
   Notas.remove(index0);
   this.fireIntervalRemoved(index0, getSize(), getSize()+1);
 }
    public Nota getNota(int index){
  return Notas.get(index);
}

}