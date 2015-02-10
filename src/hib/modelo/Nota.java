
package hib.modelo;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los objetos Nota de la bbdd.
 */
@Entity
@Table(name = "nota")
@NamedQueries({
    @NamedQuery(name = "Nota.findAll", query = "SELECT n FROM Nota n"),
    @NamedQuery(name = "Nota.findById", query = "SELECT n FROM Nota n WHERE n.id = :id"),
    @NamedQuery(name = "Nota.findByFecha", query = "SELECT n FROM Nota n WHERE n.fecha = :fecha"),
    @NamedQuery(name = "Nota.findByImportancia", query = "SELECT n FROM Nota n WHERE n.importancia = :importancia"),
    @NamedQuery(name = "Nota.findByResumen", query = "SELECT n FROM Nota n WHERE n.resumen = :resumen")})
public class Nota implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "fecha")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fecha;
    @Lob
    @Column(name = "texto")
    private String texto;
    @Column(name = "importancia")
    private String importancia;
    @Basic(optional = false)
    @Column(name = "resumen")
    private String resumen;
    @JoinColumn(name = "contacto_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contacto contactoId;

    public Nota() {
    }

    public Nota(Integer id) {
        this.id = id;
    }

    public Nota(Integer id, String resumen) {
        this.id = id;
        this.resumen = resumen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public String getTexto() {
        return texto;
    }

    public void setTexto(String texto) {
        this.texto = texto;
    }

    public String getImportancia() {
        return importancia;
    }

    public void setImportancia(String importancia) {
        this.importancia = importancia;
    }

    public String getResumen() {
        return resumen;
    }

    public void setResumen(String resumen) {
        this.resumen = resumen;
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
        if (!(object instanceof Nota)) {
            return false;
        }
        Nota other = (Nota) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hib.modelo.Nota[ id=" + id + " ]";
    }
    
}
