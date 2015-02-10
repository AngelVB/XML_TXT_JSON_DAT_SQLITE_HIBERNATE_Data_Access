
package hib.modelo;

import hib.modelo.Evento;
import java.util.ArrayList;
import javax.swing.AbstractListModel;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los modelos de las listas de eventos.
 * Utilizada para poder alamcenar Objetos Evento en un JList
 */
public class ListModelEvento extends AbstractListModel{

    private ArrayList<Evento> Eventos = new ArrayList<>();
    
    @Override
    public int getSize() {
       return Eventos.size();
     }

    @Override
    public Object getElementAt(int index) {
     Evento e = Eventos.get(index);
   return e.getTitulo();   
    
    }
    
    public void addEvento(Evento e){
  Eventos.add(e);
  this.fireIntervalAdded(this, getSize(), getSize()+1);
 }
    
    public void eliminarEvento(int index0){
   Eventos.remove(index0);
   this.fireIntervalRemoved(index0, getSize(), getSize()+1);
 }
    public Evento getEvento(int index){
  return Eventos.get(index);
}

}