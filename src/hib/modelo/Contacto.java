
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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los objetos de tipo contacto.
 */
@Entity
@Table(name = "contacto")
@NamedQueries({
    @NamedQuery(name = "Contacto.findAll", query = "SELECT c FROM Contacto c"),
    @NamedQuery(name = "Contacto.findById", query = "SELECT c FROM Contacto c WHERE c.id = :id"),
    @NamedQuery(name = "Contacto.findByNombre", query = "SELECT c FROM Contacto c WHERE c.nombre = :nombre"),
    @NamedQuery(name = "Contacto.findByTelefono", query = "SELECT c FROM Contacto c WHERE c.telefono = :telefono"),
    @NamedQuery(name = "Contacto.findByEmail", query = "SELECT c FROM Contacto c WHERE c.email = :email"),
    @NamedQuery(name = "Contacto.findByUser", query = "SELECT c FROM Contacto c WHERE c.user = :user"),
    @NamedQuery(name = "Contacto.findByUserPassword", query = "SELECT c FROM Contacto c WHERE c.user = :user AND c.password=:password"),
    @NamedQuery(name = "Contacto.findByPassword", query = "SELECT c FROM Contacto c WHERE c.password = :password")})
public class Contacto implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Column(name = "nombre")
    private String nombre;
    @Column(name = "telefono")
    private String telefono;
    @Column(name = "email")
    private String email;
    @Column(name = "user")
    private String user;
    @Column(name = "password")
    private String password;
    @JoinTable(name = "contacto_has_contacto", joinColumns = {
        @JoinColumn(name = "contacto_id", referencedColumnName = "id")}, inverseJoinColumns = {
        @JoinColumn(name = "contacto_id1", referencedColumnName = "id")})
    @ManyToMany
    private Collection<Contacto> contactoCollection;
    @ManyToMany(mappedBy = "contactoCollection")
    private Collection<Contacto> contactoCollection1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contacto")
    private Collection<ContactoHasEvento> contactoHasEventoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactoId")
    private Collection<Evento> eventoCollection;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "contactoId")
    private Collection<Nota> notaCollection;

    public Contacto() {
    }

    public Contacto(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Collection<Contacto> getContactoCollection() {
        return contactoCollection;
    }

    public void setContactoCollection(Collection<Contacto> contactoCollection) {
        this.contactoCollection = contactoCollection;
    }

    public Collection<Contacto> getContactoCollection1() {
        return contactoCollection1;
    }

    public void setContactoCollection1(Collection<Contacto> contactoCollection1) {
        this.contactoCollection1 = contactoCollection1;
    }

    public Collection<ContactoHasEvento> getContactoHasEventoCollection() {
        return contactoHasEventoCollection;
    }

    public void setContactoHasEventoCollection(Collection<ContactoHasEvento> contactoHasEventoCollection) {
        this.contactoHasEventoCollection = contactoHasEventoCollection;
    }

    public Collection<Evento> getEventoCollection() {
        return eventoCollection;
    }

    public void setEventoCollection(Collection<Evento> eventoCollection) {
        this.eventoCollection = eventoCollection;
    }

    public Collection<Nota> getNotaCollection() {
        return notaCollection;
    }

    public void setNotaCollection(Collection<Nota> notaCollection) {
        this.notaCollection = notaCollection;
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
        if (!(object instanceof Contacto)) {
            return false;
        }
        Contacto other = (Contacto) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "hib.modelo.Contacto[ id=" + id + " ]";
    }
    
}
