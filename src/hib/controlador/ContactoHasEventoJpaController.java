
package hib.controlador;

import hib.controlador.exceptions.NonexistentEntityException;
import hib.controlador.exceptions.PreexistingEntityException;
import java.io.Serializable;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import hib.modelo.Evento;
import hib.modelo.Contacto;
import hib.modelo.ContactoHasEvento;
import hib.modelo.ContactoHasEventoPK;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;

/**
 *
 * @author Angel Valera y Pablo Pardo
 * Clase que gestiona los controladores de la clase ContactoHasEvento.
 * Estos objetos se refieren a la tabla que surge de la relación M:M entre contacto y evento
 * 
 */
public class ContactoHasEventoJpaController implements Serializable {

    public ContactoHasEventoJpaController(EntityManagerFactory emf) {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ContactoHasEvento contactoHasEvento) throws PreexistingEntityException, Exception {
        if (contactoHasEvento.getContactoHasEventoPK() == null) {
            contactoHasEvento.setContactoHasEventoPK(new ContactoHasEventoPK());
        }
        contactoHasEvento.getContactoHasEventoPK().setContactoId(contactoHasEvento.getContacto().getId());
        contactoHasEvento.getContactoHasEventoPK().setEventoId(contactoHasEvento.getEvento().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            Evento evento = contactoHasEvento.getEvento();
            if (evento != null) {
                evento = em.getReference(evento.getClass(), evento.getId());
                contactoHasEvento.setEvento(evento);
            }
            Contacto contacto = contactoHasEvento.getContacto();
            if (contacto != null) {
                contacto = em.getReference(contacto.getClass(), contacto.getId());
                contactoHasEvento.setContacto(contacto);
            }
            em.persist(contactoHasEvento);
            if (evento != null) {
                evento.getContactoHasEventoCollection().add(contactoHasEvento);
                evento = em.merge(evento);
            }
            if (contacto != null) {
                contacto.getContactoHasEventoCollection().add(contactoHasEvento);
                contacto = em.merge(contacto);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            if (findContactoHasEvento(contactoHasEvento.getContactoHasEventoPK()) != null) {
                throw new PreexistingEntityException("ContactoHasEvento " + contactoHasEvento + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * Método para editar los objetos ContactoHasEvento
 * @param contactoHasEvento
 * @throws NonexistentEntityException
 * @throws Exception 
 */
    public void edit(ContactoHasEvento contactoHasEvento) throws NonexistentEntityException, Exception {
        contactoHasEvento.getContactoHasEventoPK().setContactoId(contactoHasEvento.getContacto().getId());
        contactoHasEvento.getContactoHasEventoPK().setEventoId(contactoHasEvento.getEvento().getId());
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContactoHasEvento persistentContactoHasEvento = em.find(ContactoHasEvento.class, contactoHasEvento.getContactoHasEventoPK());
            Evento eventoOld = persistentContactoHasEvento.getEvento();
            Evento eventoNew = contactoHasEvento.getEvento();
            Contacto contactoOld = persistentContactoHasEvento.getContacto();
            Contacto contactoNew = contactoHasEvento.getContacto();
            if (eventoNew != null) {
                eventoNew = em.getReference(eventoNew.getClass(), eventoNew.getId());
                contactoHasEvento.setEvento(eventoNew);
            }
            if (contactoNew != null) {
                contactoNew = em.getReference(contactoNew.getClass(), contactoNew.getId());
                contactoHasEvento.setContacto(contactoNew);
            }
            contactoHasEvento = em.merge(contactoHasEvento);
            if (eventoOld != null && !eventoOld.equals(eventoNew)) {
                eventoOld.getContactoHasEventoCollection().remove(contactoHasEvento);
                eventoOld = em.merge(eventoOld);
            }
            if (eventoNew != null && !eventoNew.equals(eventoOld)) {
                eventoNew.getContactoHasEventoCollection().add(contactoHasEvento);
                eventoNew = em.merge(eventoNew);
            }
            if (contactoOld != null && !contactoOld.equals(contactoNew)) {
                contactoOld.getContactoHasEventoCollection().remove(contactoHasEvento);
                contactoOld = em.merge(contactoOld);
            }
            if (contactoNew != null && !contactoNew.equals(contactoOld)) {
                contactoNew.getContactoHasEventoCollection().add(contactoHasEvento);
                contactoNew = em.merge(contactoNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex) {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                ContactoHasEventoPK id = contactoHasEvento.getContactoHasEventoPK();
                if (findContactoHasEvento(id) == null) {
                    throw new NonexistentEntityException("The contactoHasEvento with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }
/**
 * Método para borrar los objetos del tipo ContactoHasEvento
 * @param id
 * @throws NonexistentEntityException 
 */
    public void destroy(ContactoHasEventoPK id) throws NonexistentEntityException {
        EntityManager em = null;
        try {
            em = getEntityManager();
            em.getTransaction().begin();
            ContactoHasEvento contactoHasEvento;
            try {
                contactoHasEvento = em.getReference(ContactoHasEvento.class, id);
                contactoHasEvento.getContactoHasEventoPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The contactoHasEvento with id " + id + " no longer exists.", enfe);
            }
            Evento evento = contactoHasEvento.getEvento();
            if (evento != null) {
                evento.getContactoHasEventoCollection().remove(contactoHasEvento);
                evento = em.merge(evento);
            }
            Contacto contacto = contactoHasEvento.getContacto();
            if (contacto != null) {
                contacto.getContactoHasEventoCollection().remove(contactoHasEvento);
                contacto = em.merge(contacto);
            }
            em.remove(contactoHasEvento);
            em.getTransaction().commit();
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<ContactoHasEvento> findContactoHasEventoEntities() {
        return findContactoHasEventoEntities(true, -1, -1);
    }

    public List<ContactoHasEvento> findContactoHasEventoEntities(int maxResults, int firstResult) {
        return findContactoHasEventoEntities(false, maxResults, firstResult);
    }

    private List<ContactoHasEvento> findContactoHasEventoEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(ContactoHasEvento.class));
            Query q = em.createQuery(cq);
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ContactoHasEvento findContactoHasEvento(ContactoHasEventoPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ContactoHasEvento.class, id);
        } finally {
            em.close();
        }
    }

    public int getContactoHasEventoCount() {
        EntityManager em = getEntityManager();
        try {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<ContactoHasEvento> rt = cq.from(ContactoHasEvento.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }
    
}
