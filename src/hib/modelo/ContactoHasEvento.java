
package hib.modelo;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona la relación entre las entidades Contacto y Evento
 */
@Entity
@Table(name = "contacto_has_evento")
@NamedQueries({
    @NamedQuery(name = "ContactoHasEvento.findAll", query = "SELECT c FROM ContactoHasEvento c"),
    @NamedQuery(name = "ContactoHasEvento.findByContactoId", query = "SELECT c FROM ContactoHasEvento c WHERE c.contactoHasEventoPK.contactoId = :contactoId"),
    @NamedQuery(name = "ContactoHasEvento.findByEventoId", query = "SELECT c FROM ContactoHasEvento c WHERE c.contactoHasEventoPK.eventoId = :eventoId"),
    @NamedQuery(name = "ContactoHasEvento.findByAcude", query = "SELECT count(c.acude) FROM ContactoHasEvento c WHERE c.acude = :acude AND c.contactoHasEventoPK.eventoId = :eventoId")})
public class ContactoHasEvento implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ContactoHasEventoPK contactoHasEventoPK;
    @Column(name = "acude")
    private int acude;
    @JoinColumn(name = "evento_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Evento evento;
    @JoinColumn(name = "contacto_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contacto contacto;

    public ContactoHasEvento() {
    }

    public ContactoHasEvento(ContactoHasEventoPK contactoHasEventoPK) {
        this.contactoHasEventoPK = contactoHasEventoPK;
    }

    public ContactoHasEvento(int contactoId, int eventoId) {
        this.contactoHasEventoPK = new ContactoHasEventoPK(contactoId, eventoId);
    }

    public ContactoHasEventoPK getContactoHasEventoPK() {
        return contactoHasEventoPK;
    }

    public void setContactoHasEventoPK(ContactoHasEventoPK contactoHasEventoPK) {
        this.contactoHasEventoPK = contactoHasEventoPK;
    }

    public int getAcude() {
        return acude;
    }

    public void setAcude(int acude) {
        this.acude = acude;
    }

    public Evento getEvento() {
        return evento;
    }

    public void setEvento(Evento evento) {
        this.evento = evento;
    }

    public Contacto getContacto() {
        return contacto;
    }

    public void setContacto(Contacto contacto) {
        this.contacto = contacto;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (contactoHasEventoPK != null ? contactoHasEventoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContactoHasEvento)) {
            return false;
        }
        ContactoHasEvento other = (ContactoHasEvento) object;
        if ((this.contactoHasEventoPK == null && other.contactoHasEventoPK != null) || (this.contactoHasEventoPK != null && !this.contactoHasEventoPK.equals(other.contactoHasEventoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hib.modelo.ContactoHasEvento[ contactoHasEventoPK=" + contactoHasEventoPK + " ]";
    }
    
}
