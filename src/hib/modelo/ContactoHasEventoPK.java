
package hib.modelo;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona las claves primarias de relación entre Contacto y Eveneto
 */
@Embeddable
public class ContactoHasEventoPK implements Serializable {
    @Basic(optional = false)
    @Column(name = "contacto_id")
    private int contactoId;
    @Basic(optional = false)
    @Column(name = "evento_id")
    private int eventoId;

    public ContactoHasEventoPK() {
    }

    public ContactoHasEventoPK(int contactoId, int eventoId) {
        this.contactoId = contactoId;
        this.eventoId = eventoId;
    }

    public int getContactoId() {
        return contactoId;
    }

    public void setContactoId(int contactoId) {
        this.contactoId = contactoId;
    }

    public int getEventoId() {
        return eventoId;
    }

    public void setEventoId(int eventoId) {
        this.eventoId = eventoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) contactoId;
        hash += (int) eventoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactoHasEventoPK)) {
            return false;
        }
        ContactoHasEventoPK other = (ContactoHasEventoPK) object;
        if (this.contactoId != other.contactoId) {
            return false;
        }
        if (this.eventoId != other.eventoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hib.modelo.ContactoHasEventoPK[ contactoId=" + contactoId + ", eventoId=" + eventoId + " ]";
    }
    
}
