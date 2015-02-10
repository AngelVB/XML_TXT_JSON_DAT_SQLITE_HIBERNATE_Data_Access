
package hib.modelo;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los objetos Lugar de la bbdd.
 */
@Entity
@Table(name = "lugar")
@NamedQueries({
    @NamedQuery(name = "Lugar.findAll", query = "SELECT l FROM Lugar l"),
    @NamedQuery(name = "Lugar.findById", query = "SELECT l FROM Lugar l WHERE l.id = :id"),
    @NamedQuery(name = "Lugar.findByCalle", query = "SELECT l FROM Lugar l WHERE l.calle = :calle"),
    @NamedQuery(name = "Lugar.findByCodpost", query = "SELECT l FROM Lugar l WHERE l.codpost = :codpost")})
public class Lugar implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "calle")
    private String calle;
    @Column(name = "codpost")
    private String codpost;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "lugarId")
    private Collection<Evento> eventoCollection;

    public Lugar() {
    }

    public Lugar(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCalle() {
        return calle;
    }

    public void setCalle(String calle) {
        this.calle = calle;
    }

    public String getCodpost() {
        return codpost;
    }

    public void setCodpost(String codpost) {
        this.codpost = codpost;
    }

    public Collection<Evento> getEventoCollection() {
        return eventoCollection;
    }

    public void setEventoCollection(Collection<Evento> eventoCollection) {
        this.eventoCollection = eventoCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Lugar)) {
            return false;
        }
        Lugar other = (Lugar) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return calle;
    }
    
}
