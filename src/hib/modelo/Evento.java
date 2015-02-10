
package hib.modelo;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los objetos Evento de la bbdd.
 */
@Entity
@Table(name = "evento")
@NamedQueries({
    @NamedQuery(name = "Evento.findAll", query = "SELECT e FROM Evento e"),
    @NamedQuery(name = "Evento.findById", query = "SELECT e FROM Evento e WHERE e.id = :id"),
    @NamedQuery(name = "Evento.findByDescripcion", query = "SELECT e FROM Evento e WHERE e.descripcion = :descripcion"),
    @NamedQuery(name = "Evento.findByInicio", query = "SELECT e FROM Evento e WHERE e.inicio = :inicio"),
    @NamedQuery(name = "Evento.findByFinaliza", query = "SELECT e FROM Evento e WHERE e.finaliza = :finaliza"),
    @NamedQuery(name = "Evento.findByTitulo", query = "SELECT e FROM Evento e WHERE e.titulo = :titulo")})
public class Evento implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "descripcion")
    private String descripcion;
    @Column(name = "inicio")
    @Temporal(TemporalType.TIMESTAMP)
    private Date inicio;
    @Column(name = "finaliza")
    @Temporal(TemporalType.TIMESTAMP)
    private Date finaliza;
    @Column(name = "titulo")
    private String titulo;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "evento")
    private Collection<ContactoHasEvento> contactoHasEventoCollection;
    @JoinColumn(name = "lugar_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Lugar lugarId;
    @JoinColumn(name = "contacto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contacto contactoId;

    public Evento() {
    }

    public Evento(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Date getInicio() {
        return inicio;
    }

    public void setInicio(Date inicio) {
        this.inicio = inicio;
    }

    public Date getFinaliza() {
        return finaliza;
    }

    public void setFinaliza(Date finaliza) {
        this.finaliza = finaliza;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public Collection<ContactoHasEvento> getContactoHasEventoCollection() {
        return contactoHasEventoCollection;
    }

    public void setContactoHasEventoCollection(Collection<ContactoHasEvento> contactoHasEventoCollection) {
        this.contactoHasEventoCollection = contactoHasEventoCollection;
    }

    public Lugar getLugarId() {
        return lugarId;
    }

    public void setLugarId(Lugar lugarId) {
        this.lugarId = lugarId;
    }

    public Contacto getContactoId() {
        return contactoId;
    }

    public void setContactoId(Contacto contactoId) {
        this.contactoId = contactoId;
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
        if (!(object instanceof Evento)) {
            return false;
        }
        Evento other = (Evento) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hib.modelo.Evento[ id=" + id + " ]";
    }
    
}
